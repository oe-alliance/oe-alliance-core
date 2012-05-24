require linux-vuplus-3.1.1.inc

MACHINE_KERNEL_PR_append = ".6"

SRC_URI += "\
        file://linux_3.1.1_vuuno.patch;striplevel=1 \
	file://linux-sata_brcm.patch;striplevel=1 \	
	"

