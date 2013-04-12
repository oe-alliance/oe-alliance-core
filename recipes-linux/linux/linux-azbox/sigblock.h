/*****************************************
 Copyright (c) 2001-2005
 Sigma Designs, Inc. All Rights Reserved
 Proprietary and Confidential
 *****************************************/

#ifndef __SIG_BLOCK_H__
#define __SIG_BLOCK_H__

#define DEFAULT_IRQ_ROUTE1	0x55555555 /* All PCI IRQs route to LOG2_CPU_PCI_INTB */
#define DEFAULT_IRQ_ROUTE2	0x00030000 /* ISA IDE uses LOG2_CPU_PCU_INTD, Timing slot 0 */
#define DEFAULT_IRQ_RISE_EDGE_LO	0xff284a00 /* IRQ14 active low level, IRQ19/21 active riseedge */
#define DEFAULT_IRQ_RISE_EDGE_HI	0x00000000 /* IRQ14 active low level, IRQ19/21 active rise edge */
#define DEFAULT_IRQ_FALL_EDGE_LO	0x00004000
#define DEFAULT_IRQ_FALL_EDGE_HI	0x00000000
#define DEFAULT_IRQ_GPIO_MAP	0x0607080d /* ISA IDE/GPIO 6, PCI/GPIO 8 */
#define DEFAULT_DEV_ENABLED	0x00003cf7 /* ISAIDE/BMIDE/PCIHOST/IR/FIP/I2CM/I2CS/PCI1-4 enabled */
#define DEFAULT_PB_DEF_TIMING	0x01090008
#define DEFAULT_PB_CS_CONFIG	0x00001044
#define DEFAULT_PB_TIMING0	0x01090008
#define DEFAULT_PB_USE_TIMING0	0x000001f4
#define DEFAULT_PB_TIMING1	0x00110101
#define DEFAULT_PB_USE_TIMING1	0x000003f3
#define DEFAULT_PB_TIMING2	0
#define DEFAULT_PB_USE_TIMING2	0
#define DEFAULT_PB_TIMING3	0
#define DEFAULT_PB_USE_TIMING3	0
#define DEFAULT_PB_TIMING4	0
#define DEFAULT_PB_USE_TIMING4	0
#define DEFAULT_PB_TIMING5	0
#define DEFAULT_PB_USE_TIMING5	0

#define DEFAULT_SYSCLK_PLL	0x0 /* Set by XOS */
#define DEFAULT_SYSCLK_DIV	0x0 /* Not changed */

#define DEFAULT_ETH_MAC_HI	0x00000000 /* MAC address */
#define DEFAULT_ETH_MAC_LO	0x00000000

/* This list of devices in the enable list field (irq_route2) */
#define ISAIDE_SHIFT		0
#define BMIDE_SHIFT		1
#define PCIHOST_SHIFT		2
#define ETHERNET_SHIFT		3
#define IR_SHIFT		4
#define FIP_SHIFT		5	
#define I2CM_SHIFT		6
#define I2CS_SHIFT		7
#define SDIO_SHIFT		8
#define USB_SHIFT		9
#define PCI1_SHIFT		10
#define PCI2_SHIFT		11
#define PCI3_SHIFT		12
#define PCI4_SHIFT		13
#define PCI5_SHIFT		14
#define PCI6_SHIFT		15
#define SATA_SHIFT		16
/*				17-32: undefined */

#ifndef __ASSEMBLY__

struct hwinfo {
	unsigned long sysclk_pll; /* The setting for the PLL */
	unsigned long sysclk_div;

	/* Only 4 IRQs can be used for PCI devices (LOG2_CPU_PCI_INTA-D,
	   so we can encode it in 2 bits. Each device can have 4 IRQ
	   routing and as as result we can use one byte to represent
	   the IRQ route for a given device (IDSELx). Bu using 8 bytes,
	   we can represent the PCI devices (IDSEL1-6, 5-6 reserved) as
	   well as ISA IDE information and device enabling list*/
	unsigned long irq_route1;	/* PCI dev 1-4 */

	/* PCI dev 5-6, ISA IDE information, device enabling list */
	unsigned long irq_route2;	/* PCI dev 5-6: bit 0-15 */
					/* ISA IDE: bit 16-17: IRQ offset */

	unsigned long irq_rise_edge_hi; /* Rising edge config */
	unsigned long irq_rise_edge_lo; /* Rising edge config */
	unsigned long irq_fall_edge_hi; /* Falling edge config */
	unsigned long irq_fall_edge_lo; /* Falling edge config */

	unsigned long gpio_irq_map; /* GPIO pins hook to IRQ13..16 */
	unsigned long dev_enabled;  /* Device enabling list*/

	unsigned long pb_def_timing;
	unsigned long pb_cs_config;
	unsigned long pb_timing0;
	unsigned long pb_use_timing0;
	unsigned long pb_timing1;
	unsigned long pb_use_timing1;
	unsigned long pb_timing2;
	unsigned long pb_use_timing2;
	unsigned long pb_timing3;
	unsigned long pb_use_timing3;
	unsigned long pb_timing4;
	unsigned long pb_use_timing4;
	unsigned long pb_timing5;
	unsigned long pb_use_timing5;

	unsigned long mac_hi;	/* Ethernet MAC address */
	unsigned long mac_lo;
};

/* Definition of signature block (192bytes), which should start at 0xbfc00000 */
/* There'll be 20bytes sha1sum afterward (0xbfc000c0-0xbfc000d3) */
struct signature_block {
	unsigned long opcodes[2];  /* For opcodes, fixed value 0x10000034/0x00000000 */
	struct hwinfo hwinfo;
	/* 
	   zboot or such specific extensions 

	   Note that YAMON requires extension[2]=0x1105e0 (product ID `thirdparty')
	 */
	unsigned long extension[20];	
};

//RMmustBeEqual(sizeof(struct signature_block),3*64,seed0);

#ifdef __EMHWLIB_REGISTERS_TANGO2_H__
static inline int isaide_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> ISAIDE_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int bmide_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> BMIDE_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int sata_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> SATA_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int pci_host_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> PCIHOST_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int ethernet_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> ETHERNET_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int ir_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> IR_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int fip_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> FIP_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int i2cm_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> I2CM_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int i2cs_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> I2CS_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int sdio_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> SDIO_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int usb_enabled(const struct signature_block *sigptr)
{
	return(((((sigptr->hwinfo.dev_enabled) >> USB_SHIFT) & 1) != 0) ? 1 : 0);
}

static inline int pcidev_enabled(const struct signature_block *sigptr, int pci_idsel)
{
	return(((((sigptr->hwinfo.dev_enabled) >> (pci_idsel - 1 + PCI1_SHIFT)) & 1) != 0) ? 1 : 0);
}

/* Given PCI device idsel number and INT number, returning the IRQ number
   for it */
static inline int pcidev_intr_num(const struct signature_block *sigptr,
				const int pci_idsel, const int int_num)
{
	unsigned long route;
	int irq;

	if (pcidev_enabled(sigptr, pci_idsel) == 0)
		return(-1);
	else if ((pci_idsel >= 1) && (pci_idsel <= 4)) {
		/* Get the routing information for specific device */
		route = ((sigptr->hwinfo.irq_route1) >> ((pci_idsel - 1) * 8)) & 0xff;
		irq = (int)((route >> (int_num * 2)) & 0x3); /* int_num: 0-3 = INTA-D */
	} else if ((pci_idsel >= 5) && (pci_idsel <= 6)) {
		/* Get the routing information for specific device */
		route = ((sigptr->hwinfo.irq_route2) >> ((pci_idsel - 5) * 8)) & 0xff;
		irq = (int)((route >> (int_num * 2)) & 0x3); /* int_num: 0-3 = INTA-D */
	} else 
		return(-1);

	return(LOG2_CPU_PCI_INTA + irq);
}
 
/* Find out the CS# used by ISA IDE */
static inline int isaide_cs_select(const struct signature_block *sigptr)
{
	unsigned long cs_config = (sigptr->hwinfo.pb_cs_config >> 12) & 0xf;
	int i;

	if (isaide_enabled(sigptr) == 0)
		return(-1);

	for (i = 0; i < 4; i++) {
		if ((cs_config & 0x1) != 0) 
			return(i);
		else
			cs_config >>= 1;
	}
	return(-1);
}

/* Return the IRQ number for ISA IDE */
static inline int isaide_intr_num(const struct signature_block *sigptr)
{
	int irq;

	if (isaide_enabled(sigptr) == 0)
		return(-1);
	else
		irq = (int)(((sigptr->hwinfo.irq_route2) >> 16) & 0x3);
	return(LOG2_CPU_PCI_INTA + irq);
}

static inline int isaide_timing_slot(const struct signature_block *sigptr)
{
	unsigned long slot;

	slot = ((sigptr->hwinfo.irq_route2) >> 18) & 0x7;
	return((int)slot);
}
#endif /* __EMHWLIB_REGISTERS_TANGO2_H__ */

#endif /* !__ASSEMBLY__ */

#endif /* !__SIG_BLOCK_H__ */

