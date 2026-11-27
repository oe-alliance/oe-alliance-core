// SPDX-License-Identifier: GPL-2.0-only
/* Program the stock SM1 always-on CPU with the original GTX remote power key. */
#include <linux/module.h>
#include <linux/of.h>
#include <linux/platform_device.h>
#include <linux/amlogic/aml_mbox.h>
#include <linux/amlogic/aml_mbox_cmd.h>

/* Original Amlogic SCPI client IDs (common_drivers/drivers/bootloader). */
#define GTX_SCPI_CL_REMOTE 5

static int gtx_ir_wake_probe(struct platform_device *pdev)
{
	struct device *dev = &pdev->dev;
	struct mbox_chan *chan;
	/* NEC bytes on the wire: address 03, inverse FC, command 11, inverse EE. */
	u32 power_key = 0xee11fc03;
	int ret;

	if (!of_machine_is_compatible("gtmedia,gtx-combo"))
		return -ENODEV;
	chan = aml_mbox_request_channel_byname(dev, "ree2aocpu");
	if (IS_ERR(chan))
		return dev_err_probe(dev, PTR_ERR(chan), "AO mailbox\n");
	if (!chan)
		return -ENODEV;
	ret = aml_mbox_transfer_data_old(chan, MBOX_CMD_SET_USR_DATA,
					 GTX_SCPI_CL_REMOTE, &power_key, sizeof(power_key),
					 NULL, 0, MBOX_SYNC);
	mbox_free_channel(chan);
	if (ret < 0)
		return dev_err_probe(dev, ret, "send IR power key\n");
	dev_info(dev, "sent NEC power key %08x to stock AO firmware\n", power_key);
	return 0;
}

static const struct of_device_id gtx_ir_wake_ids[] = {
	{ .compatible = "coreelec,bl30_manager" },
	{}
};
MODULE_DEVICE_TABLE(of, gtx_ir_wake_ids);

static struct platform_driver gtx_ir_wake_driver = {
	.probe = gtx_ir_wake_probe,
	.driver = { .name = "gtx-ir-wake", .of_match_table = gtx_ir_wake_ids },
};
module_platform_driver(gtx_ir_wake_driver);
MODULE_LICENSE("GPL");
