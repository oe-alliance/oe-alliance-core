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
#include <linux/version.h>
#if LINUX_VERSION_CODE > KERNEL_VERSION(3,10,1)
#include <linux/seq_file.h>
#endif

static struct proc_dir_entry* Our_Proc_Dir;
static struct proc_dir_entry *proc_vumodel;

DEFINE_MUTEX(vumodel_table_mutex);

#if LINUX_VERSION_CODE < KERNEL_VERSION(3,10,1)
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
        remove_proc_entry( "stb/info/vumodel", NULL);
}
#else
static int proc_vumodel_show(struct seq_file *seq, void *v)
{
        off_t   begin = 0;

        seq_printf(seq, "ultimo\n");
        return 0;
}

int proc_vumodel_open(struct inode *inode, struct file *file)
{ 
	return single_open(file, proc_vumodel_show, PDE_DATA(inode));
}

static const struct file_operations proc_fops = {
	.owner		= THIS_MODULE,
	.open		= proc_vumodel_open,
	.read		= seq_read,
	.llseek		= seq_lseek,
	.release	= single_release,
};

static int __init init_vumodel(void)
{

	proc_vumodel = proc_create_data( "stb/info/vumodel", 0666, NULL, &proc_fops, NULL );
        return 0;
}

static void __exit cleanup_vumodel(void)
{
        remove_proc_entry( "stb/info/vumodel", NULL);
}
#endif
module_init(init_vumodel);
module_exit(cleanup_vumodel);

MODULE_LICENSE("proprietary");
MODULE_AUTHOR("Dr. Ideal");
MODULE_DESCRIPTION("BoxModelProc Helper");