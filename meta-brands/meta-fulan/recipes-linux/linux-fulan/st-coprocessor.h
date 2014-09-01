/* 
 * Copyright (C) 2003-2004 Giuseppe Cavallaro (peppe.cavallaro@st.com)
 *
 * May be copied or modified under the terms of the GNU General Public
 * License.  See linux/COPYING for more information.                            
 */

#include <linux/ioctl.h>

#define	MEGA			(1024 * 1024)
typedef unsigned long kaddr_t;

/* IOCTL parameters */

typedef struct {
	char	    name[16];		/* coprocessor name		    */
	u_int	    flags;		/* control flags 		    */
					/* Coprocessor region:              */
	kaddr_t	    ram_start;		/*   Host effective address         */
	u_int	    ram_size;		/*   region size (in bytes)         */
	kaddr_t	    cp_ram_start;	/*   coprocessor effective address  */

} cop_properties_t;

#define ST_IOCTL_BASE		'l'
#define STCOP_GRANT		_IOR(ST_IOCTL_BASE, 0, u_int)
#define STCOP_RESET		_IOR(ST_IOCTL_BASE, 1, u_int)
#define STCOP_START             STCOP_GRANT
#define STCOP_PEEK		_IOR(ST_IOCTL_BASE, 2, void*)
#define STCOP_POKE		_IOW(ST_IOCTL_BASE, 3, void*)
#define STCOP_GET_PROPERTIES	_IOR(ST_IOCTL_BASE, 4, cop_properties_t*)
#define STCOP_SET_PROPERTIES	_IOW(ST_IOCTL_BASE, 5, cop_properties_t*)

#define NO_DATA		0xdeadbeef
#define UNDEFINED_DATA	NO_DATA

/* ---------------------------------------------------------------------------
 *     Generic macros
 * ------------------------------------------------------------------------ */

#define xstring(x)              string(x)
#define string(x)		#x
#define plname(x)		x
#define make_pname(x,y)		x ## y

#define	MEGA			(1024 * 1024)
#define COPROCESSOR_MAJOR	63
#define FILE_2_COP(c,f)	  	(&(c)[MINOR((f)->f_dentry->d_inode->i_rdev)])

#define OUT_PORT		2	/* Mailbox host --> cop. port */
#define IN_PORT			3	/* Mailbox cop. --> host port */
#define poke_l(v,a)		*((u_int *)(a)) = (v)
#define peek_l(a)		*((u_int *)(a))

#define HOST_ADDR(cop, off)     ((CONFIG_MEMORY_START + (cop)->ram_offset) + \
									(off))
#define HOST_NC_ADDR(cop, off)  ioremap(HOST_ADDR(cop, off),HOST_ADDR(cop, 0))
#define COPR_ADDR(cop, off)     ((cop)->cpLMI_start + (cop)->ram_offset + (off))

#ifdef CONFIG_COPROCESSOR_DEBUG
#define DPRINTK(args...)   printk(args)
#else
#define DPRINTK(args...)
#endif
#define __debug(a, b)	   _debug(a, b)

/* ---------------------------------------------------------------------------
 *     Local types
 * ------------------------------------------------------------------------ */

#define COPROC_SPACE_ALLOCATE	 0x0001	/* coprocessor RAM has been defined */
#define COPROC_IN_USE    	 0x0002	/* copr. device has been opened     */
#define COPROC_RUNNING   	 0x0004	/* coproc. is running (ioctl GRANT) */

typedef struct {
	char	    name[16];		/* Coprocessor symbolic name	    */
	u_int	    id;			/* CPU ID, now the CPU N. 	    */
	u_int	    control;		/* see flags above (driver control) */

	/*
	 * Shared RAM (LMI) start address (coprocessor view)
	 */
	u_long	    cpLMI_start;	/* LMI start address (copr. view)   */

	/*
	 * The base address of coprocessor region, both sides: host (ST40)
	 * and slave (ST231, LX,...) are build based on offset and start addr.
	 */
	u_long	    ram_offset;		/* Coprocessor RAM offset (in bytes)*/
	u_int	    ram_size;		/* Coprocessor RAM size (in bytes)  */

#ifdef CONFIG_COPROCESSOR_DEBUG
	u_int	    h2c_port;		/* comm. port: host --> coproc.     */
	u_int	    c2h_port;		/* comm. port: host <-- coproc.     */
#endif
	u_int	    irq;		/* interrup used... if any          */
	u_long	    mbox_wait;		/* CPU signature (waiting for boot) */
	u_long	    mbox_entryp;	/* where to put the entry point...  */
	u_long	    mbox_enable;	/* ... to trigger the CPU start     */
	
} coproc_t;

struct coproc_board_info {
	char *name;
	int max_coprs;
};

extern int coproc_cpu_open(coproc_t *);
extern int coproc_cpu_init(coproc_t *);
extern int coproc_cpu_grant(coproc_t *, unsigned long);
extern int coproc_cpu_release(coproc_t *);
extern int coproc_cpu_reset(coproc_t *);
extern void coproc_proc_other_info(coproc_t *, struct seq_file *);
