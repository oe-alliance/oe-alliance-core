// SPDX-License-Identifier: GPL-2.0-or-later
/*
 * Broadcom Nexus STC bridge for userspace media players.
 *
 * The closed Linux-DVB driver connects video0 to a Nexus SimpleStcChannel,
 * but its public DVB API does not provide a way for an ALSA based player to
 * drive that clock.  This small bridge deliberately contains no Broadcom
 * headers or copied SDK definitions.  It resolves the already loaded driver
 * entry points at runtime and only changes the first, documented mode word of
 * the opaque settings object (Auto = 1, Host = 2).
 */

#include <linux/fs.h>
#include <linux/kallsyms.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <linux/mutex.h>
#include <linux/slab.h>
#include <linux/string.h>
#include <linux/uaccess.h>

#define GB_STC_SETTINGS_BYTES 512
#define GB_STC_MODE_AUTO 1U
#define GB_STC_MODE_HOST 2U
#define GB_STC_MODE_OFFSET sizeof(u32)
#define GB_STC_SYNC_MODE_OFFSET 28U
#define GB_STC_SYNC_OFF 0U

typedef void *(*gb_platform_get_channel_fn)(unsigned int index);
typedef void *(*gb_primer_get_channel_fn)(void *primer, unsigned int id,
					  unsigned int generation);
typedef void (*gb_get_default_settings_fn)(unsigned int index, void *settings);
typedef void *(*gb_open_channel_fn)(unsigned int index, const void *settings);
typedef void (*gb_close_channel_fn)(void *handle);
typedef void (*gb_get_settings_fn)(void *handle, void *settings);
typedef int (*gb_set_settings_fn)(void *handle, const void *settings);
typedef int (*gb_set_stc_fn)(void *handle, u32 stc);
typedef int (*gb_freeze_fn)(void *handle, bool frozen);
typedef int (*gb_set_rate_fn)(void *handle, unsigned int increment,
                              unsigned int prescale);

static gb_platform_get_channel_fn gb_platform_get_channel;
static gb_primer_get_channel_fn gb_primer_get_channel;
static gb_get_default_settings_fn gb_get_default_settings;
static gb_open_channel_fn gb_open_channel;
static gb_close_channel_fn gb_close_channel;
static gb_get_settings_fn gb_get_settings;
static gb_set_settings_fn gb_set_settings;
static gb_set_stc_fn gb_set_stc;
static gb_freeze_fn gb_freeze;
static gb_set_rate_fn gb_set_rate;
static void *gb_direct_channel;
static bool gb_host_active;
static DEFINE_MUTEX(gb_stc_lock);

union gb_stc_settings {
	u64 alignment;
	u8 bytes[GB_STC_SETTINGS_BYTES];
};

static void *gb_stc_get_channel(void)
{
	if (gb_platform_get_channel)
		return gb_platform_get_channel(0);
	if (gb_primer_get_channel)
		return gb_primer_get_channel(NULL, 0, 0);
	return gb_direct_channel;
}

static void *gb_stc_open_direct(void)
{
	union gb_stc_settings settings;

	if (gb_direct_channel)
		return gb_direct_channel;
	if (!gb_get_default_settings || !gb_open_channel)
		return NULL;
	memset(&settings, 0, sizeof(settings));
	gb_get_default_settings(0, &settings);
	/* NEXUS_StcChannelSettings starts with timebase followed by mode. */
	*(u32 *)(settings.bytes + GB_STC_MODE_OFFSET) = GB_STC_MODE_HOST;
	gb_direct_channel = gb_open_channel(0, &settings);
	return gb_direct_channel;
}

static void gb_stc_close_direct(void)
{
	if (gb_direct_channel && gb_close_channel)
		gb_close_channel(gb_direct_channel);
	gb_direct_channel = NULL;
}

static int gb_stc_set_host(u32 stc)
{
	union gb_stc_settings settings;
	void *handle;
	int result;

	handle = gb_stc_get_channel();
	if (!handle && gb_open_channel)
		handle = gb_stc_open_direct();
	if (!handle)
		return -ENODEV;
	if (gb_platform_get_channel) {
		memset(&settings, 0, sizeof(settings));
		gb_get_settings(handle, &settings);
		*(u32 *)settings.bytes = GB_STC_MODE_HOST;
		/* The DVB driver selects AudioAdjustmentConcealment for an Enigma2
		 * audio+video pair. Kodi has no Nexus audio decoder, so retaining that
		 * path-delay equalizer makes video alternate between holds and catch-up.
		 * TSM remains active in eOff; only path-delay equalization is off. */
		*(u32 *)(settings.bytes + GB_STC_SYNC_MODE_OFFSET) = GB_STC_SYNC_OFF;
		result = gb_set_settings(handle, &settings);
		if (result) {
			pr_err("gb_stc_host: SetSettings(host) failed: %d\n", result);
			return -EIO;
		}
	}
	/* A directly opened full StcChannel uses the 90 kHz (2, 0) rate used by
	 * the legacy Broadcom userspace Nexus ABI. Existing decoder-owned channel
	 * accessors retain their receiver-specific (1, 0) rate. */
	result = gb_set_rate(handle, gb_open_channel ? 2 : 1, 0);
	if (result) {
		pr_err("gb_stc_host: SetRate(host) failed: %d\n", result);
		return -EIO;
	}
	result = gb_set_stc(handle, stc);
	if (result) {
		pr_err("gb_stc_host: SetStc(host) failed: %d\n", result);
		return -EIO;
	}
	result = gb_freeze(handle, false);
	if (result)
		pr_err("gb_stc_host: Freeze(host) failed: %d\n", result);
	if (!result)
		gb_host_active = true;
	return result ? -EIO : 0;
}

static int gb_stc_set_auto(void)
{
	union gb_stc_settings settings;
	void *handle;
	int result;

	if (!gb_host_active)
		return 0;
	gb_host_active = false;
	if (gb_open_channel) {
		gb_stc_close_direct();
		return 0;
	}
	handle = gb_stc_get_channel();
	if (!handle)
		return -ENODEV;
	/* video_primer_get_stc_channel returns the lower-level Vu+ StcChannel.
	 * We only drive its free-running counter and never change its association;
	 * VIDEO_STOP followed by Enigma2's decoder setup restores normal ownership. */
	if (gb_primer_get_channel)
		return 0;
	memset(&settings, 0, sizeof(settings));
	gb_get_settings(handle, &settings);
	*(u32 *)settings.bytes = GB_STC_MODE_AUTO;
	result = gb_set_settings(handle, &settings);
	if (result)
		pr_err("gb_stc_host: SetSettings(auto) failed: %d\n", result);
	return result ? -EIO : 0;
}

static ssize_t gb_stc_write(struct file *file, const char __user *buffer,
			    size_t count, loff_t *position)
{
	char command[64];
	unsigned int stc;
	unsigned int frozen;
	void *handle;
	int result = -EINVAL;
	size_t length;

	(void)file;
	(void)position;
	length = min(count, sizeof(command) - 1);
	if (copy_from_user(command, buffer, length))
		return -EFAULT;
	command[length] = '\0';
	strim(command);

	mutex_lock(&gb_stc_lock);
	if (sscanf(command, "host %u", &stc) == 1)
		result = gb_stc_set_host((u32)stc);
	else if (!strcmp(command, "auto"))
		result = gb_stc_set_auto();
	else if (sscanf(command, "freeze %u", &frozen) == 1) {
		handle = gb_stc_get_channel();
		result = handle ? (gb_freeze(handle, frozen != 0) ? -EIO : 0)
				: -ENODEV;
	}
	mutex_unlock(&gb_stc_lock);

	return result ? result : count;
}

static const struct file_operations gb_stc_fops = {
	.owner = THIS_MODULE,
	.write = gb_stc_write,
	.llseek = no_llseek,
};

static struct miscdevice gb_stc_device = {
	.minor = MISC_DYNAMIC_MINOR,
	.name = "stb-stc-host",
	.fops = &gb_stc_fops,
	.mode = 0660,
};

static int __init gb_stc_init(void)
{
	gb_platform_get_channel = (gb_platform_get_channel_fn)
		kallsyms_lookup_name("platform_get_stc_channel");
	if (!gb_platform_get_channel)
		gb_primer_get_channel = (gb_primer_get_channel_fn)
			kallsyms_lookup_name("video_primer_get_stc_channel");
	if (!gb_platform_get_channel && !gb_primer_get_channel) {
		gb_get_default_settings = (gb_get_default_settings_fn)
			kallsyms_lookup_name("NEXUS_StcChannel_GetDefaultSettings");
		gb_open_channel = (gb_open_channel_fn)
			kallsyms_lookup_name("NEXUS_StcChannel_Open");
		gb_close_channel = (gb_close_channel_fn)
			kallsyms_lookup_name("NEXUS_StcChannel_Close");
		if (!gb_get_default_settings || !gb_open_channel || !gb_close_channel) {
			pr_err("gb_stc_host: supported DVB STC accessor not found\n");
			return -ENODEV;
		}
	}
	gb_get_settings = (gb_get_settings_fn)kallsyms_lookup_name(
		gb_platform_get_channel ? "NEXUS_SimpleStcChannel_GetSettings" :
					  "NEXUS_StcChannel_GetSettings");
	gb_set_settings = (gb_set_settings_fn)kallsyms_lookup_name(
		gb_platform_get_channel ? "NEXUS_SimpleStcChannel_SetSettings" :
					  "NEXUS_StcChannel_SetSettings");
	gb_set_stc = (gb_set_stc_fn)kallsyms_lookup_name(
		gb_platform_get_channel ? "NEXUS_SimpleStcChannel_SetStc" :
					  "NEXUS_StcChannel_SetStc");
	gb_freeze = (gb_freeze_fn)kallsyms_lookup_name(
		gb_platform_get_channel ? "NEXUS_SimpleStcChannel_Freeze" :
					  "NEXUS_StcChannel_Freeze");
	gb_set_rate = (gb_set_rate_fn)kallsyms_lookup_name(
		gb_platform_get_channel ? "NEXUS_SimpleStcChannel_SetRate" :
					  "NEXUS_StcChannel_SetRate");
	if (!gb_get_settings || !gb_set_settings || !gb_set_stc || !gb_freeze ||
	    !gb_set_rate) {
		pr_err("gb_stc_host: required Nexus STC symbols not found\n");
		return -ENOENT;
	}
	if (!gb_open_channel && !gb_stc_get_channel()) {
		pr_err("gb_stc_host: DVB video STC channel not available\n");
		return -ENODEV;
	}
	pr_info("gb_stc_host: using %s DVB STC access\n",
		gb_platform_get_channel ? "platform" :
		gb_primer_get_channel ? "video-primer" : "direct Nexus");
	return misc_register(&gb_stc_device);
}

static void __exit gb_stc_exit(void)
{
	gb_stc_close_direct();
	misc_deregister(&gb_stc_device);
}

module_init(gb_stc_init);
module_exit(gb_stc_exit);

MODULE_DESCRIPTION("Broadcom Nexus host-STC bridge for Kodi");
MODULE_AUTHOR("OE-Alliance contributors");
MODULE_LICENSE("GPL");
