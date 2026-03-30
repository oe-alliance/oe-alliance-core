ifneq ($(KERNELRELEASE),)

-include $(src)/tbs-kernel-compat.mk

obj-m += $(TBS_KBUILD_MODULES)

ifneq ($(strip $(TBS_COMPOSITE_MODULE)),)
$(TBS_COMPOSITE_MODULE)-objs := $(TBS_COMPOSITE_OBJECTS)
endif

ifneq ($(strip $(TBS_COMPOSITE_MODULE_2)),)
$(TBS_COMPOSITE_MODULE_2)-objs := $(TBS_COMPOSITE_OBJECTS_2)
endif

ccflags-y += -I$(srctree)/drivers/media/usb/dvb-usb
ccflags-y += -I$(srctree)/drivers/media/dvb-frontends
ccflags-y += -I$(srctree)/drivers/media/tuners
ccflags-y += -I$(srctree)/drivers/media/dvb-core
# Linux 3.2 receiver kernels still use the pre-media-reorganisation layout.
ccflags-y += -I$(srctree)/drivers/media/dvb/frontends
ccflags-y += -I$(srctree)/drivers/media/dvb/dvb-usb
ccflags-y += -I$(srctree)/drivers/media/dvb/dvb-core
ccflags-y += -I$(src)
ccflags-y += -include $(src)/tbs-kernel-compat.h
ccflags-y += $(TBS_CCFLAGS)

else

KERNEL_SRC ?= /lib/modules/$(shell uname -r)/build

.PHONY: all modules clean

all modules:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) KBUILD_EXTRA_SYMBOLS="$(TBS_KBUILD_EXTRA_SYMBOLS)" modules

clean:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) clean

endif
