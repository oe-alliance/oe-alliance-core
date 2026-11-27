ifneq ($(KERNELRELEASE),)
obj-m += gtx-ir-wake.o
ccflags-y += -I$(srctree)/common_drivers/include
else
all modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) $(if $(filter all,$@),modules,modules_install)
clean:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) clean
endif
