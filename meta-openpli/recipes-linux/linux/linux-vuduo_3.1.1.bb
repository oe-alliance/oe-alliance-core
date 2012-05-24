require linux-vuplus-3.1.1.inc

MACHINE_KERNEL_PR_append = ".5"

SRC_URI += "\
        file://linux_3.1.1_vuduo.patch;striplevel=1 \
	file://linux-sata_brcm.patch;striplevel=1 \
	"
