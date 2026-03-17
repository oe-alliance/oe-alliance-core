# TBS 5520SE out-of-tree kernel module build
#
# Works in two modes:
#   1) OE/Yocto: called via 'make -C <kbuild> M=<this-dir> modules'
#   2) Standalone: 'make KSRC=... KBUILD=... CROSS_COMPILE=...'

obj-m += dvb-usb-tbs5520se.o
dvb-usb-tbs5520se-objs := tbs5520se.o

obj-m += si2183.o
obj-m += av201x.o

ccflags-y += -I$(src)

# $(srctree) is set by kbuild when invoked with -C <kbuild-dir>
KSRC_DIR := $(if $(srctree),$(srctree),$(KSRC))

ccflags-y += -I$(KSRC_DIR)/drivers/media/dvb-core
ccflags-y += -I$(KSRC_DIR)/drivers/media/dvb-frontends
ccflags-y += -I$(KSRC_DIR)/drivers/media/tuners
ccflags-y += -I$(KSRC_DIR)/drivers/media/usb/dvb-usb

# Standalone targets (not used by OE)
KSRC  ?= /lib/modules/$(shell uname -r)/source
KBUILD ?= /lib/modules/$(shell uname -r)/build

standalone:
	$(MAKE) ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) -C $(KBUILD) M=$(CURDIR) modules

clean:
	$(MAKE) ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) -C $(KBUILD) M=$(CURDIR) clean
