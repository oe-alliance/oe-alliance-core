obj-m := dreammosaic.o

SRC := $(shell pwd)

all:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) KBUILD_EXTRA_SYMBOLS=$(SRC)/dmm.symvers modules

modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) modules_install

# the kernel clean would also remove the driver modules unpacked below
clean:
	rm -f *.o *.ko *.mod.c modules.order Module.symvers .*.cmd
