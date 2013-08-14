#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/slab.h>
#include <linux/string.h>
#include <linux/timer.h>
#include <linux/major.h>
#include <linux/fs.h>
#include <linux/err.h>
#include <linux/ioctl.h>
#include <linux/init.h>
#include <linux/proc_fs.h>
#include <asm/uaccess.h>
#include <linux/semaphore.h>
#include <linux/cdev.h>


static struct proc_dir_entry* Our_Proc_Dir;
static struct proc_dir_entry *proc_vumodel;

DEFINE_MUTEX(vumodel_table_mutex);


static int vumodel_read_proc (char *page, char **start, off_t off, int count, int *eof, void *data_unused)
{
        int len;
        off_t   begin = 0;

        mutex_lock(&vumodel_table_mutex);

        len = sprintf(page, "ultimo\n");
        mutex_unlock(&vumodel_table_mutex);
        if (off >= len+begin)
                return 0;
        *start = page + (off-begin);
        return ((count < begin+len-off) ? count : begin+len-off);
}

static int __init init_vumodel(void)
{

		if ((proc_vumodel = create_proc_entry( "stb/info/vumodel", 0666, NULL )))
                proc_vumodel->read_proc = vumodel_read_proc;
        return 0;
}

static void __exit cleanup_vumodel(void)
{
        if (proc_vumodel)
                remove_proc_entry( "vumodel", NULL);
}

module_init(init_vumodel);
module_exit(cleanup_vumodel);

MODULE_LICENSE("proprietary");
MODULE_AUTHOR("Dr. Ideal");
MODULE_DESCRIPTION("BoxModelProc Helper");