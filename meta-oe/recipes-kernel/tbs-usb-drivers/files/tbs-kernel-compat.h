/* SPDX-License-Identifier: GPL-2.0-only */
#ifndef TBS_KERNEL_COMPAT_H
#define TBS_KERNEL_COMPAT_H

/*
 * The OpenATV receiver matrix spans several generations of the media API.
 * These mappings are enabled by feature checks against the selected machine
 * kernel during do_configure, rather than by assuming an API from its version.
 */
#ifdef TBS_LEGACY_DVB_FREQUENCY_API
#define frequency_min_hz frequency_min
#define frequency_max_hz frequency_max
#define frequency_stepsize_hz frequency_stepsize
#define TBS_SAT_MHZ 1000UL

#ifdef TBS_LEGACY_DVB_FREQUENCY_KHZ
#define MHz 1000UL
#define kHz 1UL
#else
#define MHz 1000000UL
#define kHz 1000UL
#endif
#else
#define TBS_SAT_MHZ 1000000UL
#endif

#ifdef TBS_LEGACY_I2C_NEW_DEVICE
#define i2c_new_client_device i2c_new_device
#endif

#ifdef TBS_LEGACY_I2C_CLIENT_HAS_DRIVER
#define i2c_client_has_driver(client) ((client) && (client)->dev.driver)
#endif

#ifdef TBS_LEGACY_RC_PROTOCOL_NAMES
#define RC_PROTO_UNKNOWN RC_TYPE_UNKNOWN
#define RC_PROTO_BIT_NEC RC_BIT_NEC
#endif

#ifdef TBS_LEGACY_RC_KEYDOWN
#define tbs_rc_keydown(dev, protocol, scancode, toggle) \
	rc_keydown(dev, scancode, toggle)
#else
#define tbs_rc_keydown(dev, protocol, scancode, toggle) \
	rc_keydown(dev, protocol, scancode, toggle)
#endif

#ifdef TBS_LEGACY_STRSCPY
#define strscpy strlcpy
#endif

#ifdef TBS_LEGACY_IS_REACHABLE
#define IS_REACHABLE(option) (option)
#endif

#endif /* TBS_KERNEL_COMPAT_H */
