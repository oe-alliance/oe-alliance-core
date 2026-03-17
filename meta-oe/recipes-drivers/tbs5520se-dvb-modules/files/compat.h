#ifndef _TBS5520SE_COMPAT_H_
#define _TBS5520SE_COMPAT_H_

#include <linux/version.h>
#include <linux/i2c.h>
#include <linux/err.h>
#include <linux/dvb/frontend.h>

/*
 * Compat shims for kernel 4.8.3 (GigaBlue gb7356)
 */

/* i2c_new_client_device: wrapper around i2c_new_device for <5.2 kernels */
#ifndef i2c_new_client_device
static inline struct i2c_client *
i2c_new_client_device(struct i2c_adapter *adap,
		      struct i2c_board_info const *info)
{
	struct i2c_client *ret;

	ret = i2c_new_device(adap, info);
	if (!ret)
		return (struct i2c_client *)ERR_PTR(-ENOMEM);
	return ret;
}
#endif

static inline bool i2c_client_has_driver(struct i2c_client *client)
{
	return !IS_ERR_OR_NULL(client) && client->dev.driver;
}

/* DVB-S2X modulation types not in kernel 4.8.3 */
#ifndef APSK_8L
#define APSK_8L		APSK_16
#endif
#ifndef APSK_16L
#define APSK_16L	APSK_16
#endif
#ifndef APSK_32L
#define APSK_32L	APSK_32
#endif
#ifndef APSK_64
#define APSK_64		APSK_32
#endif
#ifndef APSK_64L
#define APSK_64L	APSK_32
#endif
#ifndef APSK_128
#define APSK_128	APSK_32
#endif
#ifndef APSK_256
#define APSK_256	APSK_32
#endif
#ifndef APSK_1024
#define APSK_1024	APSK_32
#endif

/* DVB-S2X FEC rates not in 4.8.3 */
#ifndef FEC_1_3
#define FEC_1_3		FEC_AUTO
#endif
#ifndef FEC_1_4
#define FEC_1_4		FEC_AUTO
#endif
#ifndef FEC_2_9
#define FEC_2_9		FEC_AUTO
#endif
#ifndef FEC_13_45
#define FEC_13_45	FEC_AUTO
#endif
#ifndef FEC_9_20
#define FEC_9_20	FEC_AUTO
#endif
#ifndef FEC_11_20
#define FEC_11_20	FEC_AUTO
#endif
#ifndef FEC_23_36
#define FEC_23_36	FEC_AUTO
#endif
#ifndef FEC_25_36
#define FEC_25_36	FEC_AUTO
#endif
#ifndef FEC_13_18
#define FEC_13_18	FEC_AUTO
#endif
#ifndef FEC_26_45
#define FEC_26_45	FEC_AUTO
#endif
#ifndef FEC_28_45
#define FEC_28_45	FEC_AUTO
#endif
#ifndef FEC_7_9
#define FEC_7_9		FEC_AUTO
#endif
#ifndef FEC_77_90
#define FEC_77_90	FEC_AUTO
#endif
#ifndef FEC_32_45
#define FEC_32_45	FEC_AUTO
#endif
#ifndef FEC_11_15
#define FEC_11_15	FEC_AUTO
#endif
#ifndef FEC_NONE_2
#define FEC_NONE_2	FEC_NONE
#endif

/* DVB-S2X rolloff values not in 4.8.3 */
#ifndef ROLLOFF_15
#define ROLLOFF_15	ROLLOFF_AUTO
#endif
#ifndef ROLLOFF_10
#define ROLLOFF_10	ROLLOFF_AUTO
#endif
#ifndef ROLLOFF_5
#define ROLLOFF_5	ROLLOFF_AUTO
#endif

/* MHz unit not in 4.8.3 */
#ifndef MHz
#define MHz	1000000UL
#endif
#ifndef kHz
#define kHz	1000UL
#endif

#endif /* _TBS5520SE_COMPAT_H_ */
