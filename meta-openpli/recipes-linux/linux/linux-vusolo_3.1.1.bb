require linux-vuplus-3.1.1.inc

MACHINE_KERNEL_PR_append = ".4"

SRC_URI += "\
        file://linux_3.1.1_vusolo.patch;striplevel=1 \
	file://fix_cpu_proc_solo.patch;striplevel=1 \
	"

