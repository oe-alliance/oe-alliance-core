/* stb-nimsockets - main module
        by infiniti     - 2012

*/

#include <linux/module.h> 	// Yes a kernel module ;)
#include <linux/init.h>
#include <linux/kernel.h>	// We work with kernel :D 

#include <linux/fs.h>		// for basic filesystem
#include <linux/proc_fs.h>	// for the proc filesystem
#include <linux/seq_file.h>	// for sequence files
#include <linux/stat.h>		// for acces rights defines

#include <asm/uaccess.h>	// needed for acces kernel/user memory


#include "stb-nimsockets.h"


// read/write
static int
stb_bus_nimsockets_show(struct seq_file *m, void *v)
{
    seq_printf(m, stb_bus_nimsockets_buffer);
    return 0;
}
static int
stb_bus_nimsockets_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_bus_nimsockets_show, NULL);
}

static ssize_t
stb_bus_nimsockets_write(struct file *file, const char *buffer, size_t len, loff_t * off) 
{
    stb_bus_nimsockets_buffersize=0;
    memset(&stb_bus_nimsockets_buffer[0], 0, sizeof(stb_bus_nimsockets_buffer));

    if ( len > PROCFS_MAX_SIZE ) {
	stb_bus_nimsockets_buffersize = PROCFS_MAX_SIZE;
    } else {
	stb_bus_nimsockets_buffersize = len;
    }
  
    if ( copy_from_user(stb_bus_nimsockets_buffer, buffer, stb_bus_nimsockets_buffersize) ) {
	return -EFAULT;
    }
    return stb_bus_nimsockets_buffersize;
}

static int __init 
stb_nimsockets_init(void)
{
    // read/write
    file_stb_bus_nimsockets = proc_create("bus/nim_sockets", (S_IRUGO|S_IWUGO) , NULL,
                &stb_bus_nimsockets_fops);
    if (!file_stb_bus_nimsockets) { return -ENOMEM; } 

    return 0;
}

static void __exit
stb_nimsockets_exit(void)
{
    remove_proc_entry("bus/nim_sockets", NULL);
}

module_init(stb_nimsockets_init);
module_exit(stb_nimsockets_exit);

MODULE_LICENSE("GPL"); 
