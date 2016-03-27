#ifndef _BPAMEM_H
#define _BPAMEM_H

#define BPAMEM_MAJOR 153
#define BPAMEM_MINOR 0

typedef struct
{
	char          *bpa_part;
	unsigned long  mem_size;
	unsigned int   device_num;
} BPAMemAllocMemData;

typedef struct
{
	char          *bpa_part;
	unsigned long  mem_size;
	unsigned int   device_num;
	unsigned long  phys_addr;
} BPAMemMapMemData;

#define BPAMEMIO_ALLOCMEM   _IOWR(BPAMEM_MAJOR, 0, BPAMemAllocMemData)
#define BPAMEMIO_FREEMEM    _IO  (BPAMEM_MAJOR, 1)
#define BPAMEMIO_MAPMEM     _IOWR(BPAMEM_MAJOR, 2, BPAMemMapMemData)
#define BPAMEMIO_UNMAPMEM   _IO  (BPAMEM_MAJOR, 3)

#define BPAMEM_DEBUG

#ifdef  BPAMEM_DEBUG
#define DEBUG_PRINTK(format, ...) printk("(%s): " format "\n", __func__, ## __VA_ARGS__) 
#else 
#define DEBUG_PRINTK(format,...)
#endif

#endif
