ifneq ($(KERNELRELEASE),)
obj-m += hid-gtmedia-rcu.o
else
all modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) $(if $(filter all,$@),modules,modules_install)
clean:
	$(MAKE) -C $(KERNEL_SRC) M=$(CURDIR) clean
endif
