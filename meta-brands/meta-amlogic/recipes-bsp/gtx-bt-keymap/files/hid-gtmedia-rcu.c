// SPDX-License-Identifier: GPL-2.0-only
#include <linux/hid.h>
#include <linux/input.h>
#include <linux/module.h>
#include <linux/timer.h>

struct gtx_remote {
	struct input_dev *keyboard;
	bool key_down;
	bool empty_pending;
	bool mouse_seen;
	bool mouse_ok;
	bool skip_enter_empty;
	unsigned long first_empty;
	unsigned int held_arrow;
	spinlock_t arrow_lock;
	struct timer_list arrow_timer;
};

static void key_event(struct input_dev *input, unsigned int key, int value)
{
	input_report_key(input, key, value);
	input_sync(input);
}

static void click(struct input_dev *input, unsigned int key)
{
	key_event(input, key, 1);
	key_event(input, key, 0);
}

static void release_arrow(struct gtx_remote *remote)
{
	if (remote->held_arrow) {
		key_event(remote->keyboard, remote->held_arrow, 0);
		remote->held_arrow = 0;
	}
}

static void arrow_timeout(struct timer_list *timer)
{
	struct gtx_remote *remote = from_timer(remote, timer, arrow_timer);
	unsigned long flags;

	spin_lock_irqsave(&remote->arrow_lock, flags);
	if (!timer_pending(timer))
		release_arrow(remote);
	spin_unlock_irqrestore(&remote->arrow_lock, flags);
}

static void arrow_report(struct gtx_remote *remote, int x, int y)
{
	unsigned int key = x ? (x < 0 ? KEY_LEFT : KEY_RIGHT) :
			       (y < 0 ? KEY_UP : KEY_DOWN);
	unsigned long flags;

	spin_lock_irqsave(&remote->arrow_lock, flags);
	/* A short tap is one delta of 16. A held key produces deltas of 12
	 * every 50 ms, without a final release report. Let evdev repeat it.
	 */
	if (abs(x + y) == 16) {
		release_arrow(remote);
		click(remote->keyboard, key);
	} else {
		if (remote->held_arrow != key) {
			release_arrow(remote);
			remote->held_arrow = key;
			key_event(remote->keyboard, key, 1);
		}
		mod_timer(&remote->arrow_timer, jiffies + msecs_to_jiffies(140));
	}
	spin_unlock_irqrestore(&remote->arrow_lock, flags);
}

static void keyboard_report(struct gtx_remote *remote, const u8 *data)
{
	bool down = memchr_inv(data + 1, 0, 8) != NULL;

	/* After an ordinary key a single empty report is its release. On
	 * leaving mouse mode the switch sends two empty keyboard reports,
	 * one on press and one on release, without a preceding keyboard key.
	 */
	if (!down && !remote->key_down && remote->mouse_seen) {
		if (remote->empty_pending &&
		    time_before_eq(jiffies, remote->first_empty + msecs_to_jiffies(4000))) {
			if (!remote->skip_enter_empty)
				click(remote->keyboard, KEY_TEXT);
			remote->mouse_seen = remote->skip_enter_empty;
			remote->skip_enter_empty = false;
			remote->empty_pending = false;
		} else {
			remote->first_empty = jiffies;
			remote->empty_pending = true;
		}
	} else {
		remote->empty_pending = false;
		remote->skip_enter_empty = false;
	}
	remote->key_down = down;
}

static int gtx_raw_event(struct hid_device *hdev, struct hid_report *report,
			 u8 *data, int size)
{
	struct gtx_remote *remote = hid_get_drvdata(hdev);
	int x, y;

	if (!remote || !remote->keyboard || report->type != HID_INPUT_REPORT)
		return 0;
	if (report->id == 1 && size == 9) {
		keyboard_report(remote, data);
		return 0;
	}
	if (report->id == 2) {
		remote->empty_pending = false;
		return 0;
	}
	if (report->id != 3 || size != 5)
		return 0;

	x = (s8)data[2];
	y = (s8)data[3];
	remote->mouse_seen = true;
	if (remote->mouse_ok != !!(data[1] & 1)) {
		remote->mouse_ok = !!(data[1] & 1);
		key_event(remote->keyboard, KEY_OK, remote->mouse_ok);
	}
	data[1] &= ~1;
	if (!data[1] && x == 1 && y == 1 && !data[4]) {
		/* The entry marker precedes the switch's two empty reports. */
		click(remote->keyboard, KEY_TEXT);
		remote->skip_enter_empty = true;
		remote->empty_pending = false;
		data[2] = data[3] = 0;
	} else if ((!x || !y) && (abs(x + y) == 12 || abs(x + y) == 16)) {
		arrow_report(remote, x, y);
		data[2] = data[3] = 0;
	}
	return 0;
}

static int gtx_input_configured(struct hid_device *hdev, struct hid_input *hi)
{
	struct gtx_remote *remote = hid_get_drvdata(hdev);

	if (remote && hi->application == HID_GD_KEYBOARD) {
		remote->keyboard = hi->input;
		input_set_capability(hi->input, EV_KEY, KEY_TEXT);
	}
	return 0;
}

static int gtx_probe(struct hid_device *hdev, const struct hid_device_id *id)
{
	struct gtx_remote *remote;
	int ret;

	/* Other brands use the same HID ID. Keep their generic mappings. */
	if (!strcmp(hdev->name, "GTMEDIA")) {
		remote = devm_kzalloc(&hdev->dev, sizeof(*remote), GFP_KERNEL);
		if (!remote)
			return -ENOMEM;
		spin_lock_init(&remote->arrow_lock);
		timer_setup(&remote->arrow_timer, arrow_timeout, 0);
		hid_set_drvdata(hdev, remote);
	}
	hdev->quirks |= HID_QUIRK_INPUT_PER_APP;
	ret = hid_parse(hdev);
	if (ret)
		return ret;
	return hid_hw_start(hdev, HID_CONNECT_DEFAULT);
}

static void gtx_remove(struct hid_device *hdev)
{
	struct gtx_remote *remote = hid_get_drvdata(hdev);

	if (remote)
		del_timer_sync(&remote->arrow_timer);
	hid_hw_stop(hdev);
}

static const struct hid_device_id gtx_devices[] = {
	{ HID_BLUETOOTH_DEVICE(0x0508, 0x0110) },
	{}
};
MODULE_DEVICE_TABLE(hid, gtx_devices);

static struct hid_driver gtx_driver = {
	.name = "hid-gtmedia-rcu",
	.id_table = gtx_devices,
	.probe = gtx_probe,
	.remove = gtx_remove,
	.raw_event = gtx_raw_event,
	.input_configured = gtx_input_configured,
};
module_hid_driver(gtx_driver);

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("GTMedia Bluetooth remote control for Enigma2");
