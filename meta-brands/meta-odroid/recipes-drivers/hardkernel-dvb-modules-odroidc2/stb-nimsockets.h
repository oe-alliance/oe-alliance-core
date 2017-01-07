/* stb-nimsockets - header file
	by infiniti 	- 2012

*/

#define PROCFS_MAX_SIZE		2048

// read/write
static struct proc_dir_entry* file_stb_bus_nimsockets;
static char stb_bus_nimsockets_buffer[PROCFS_MAX_SIZE];
static unsigned long stb_bus_nimsockets_buffersize = 0;

static int stb_bus_nimsockets_open(struct inode *inode, struct file *file);
static ssize_t stb_bus_nimsockets_write(struct file *file, const char *buffer, size_t len, loff_t * off);
static const struct file_operations stb_bus_nimsockets_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_bus_nimsockets_open,
    .read       = seq_read,
    .write	= stb_bus_nimsockets_write,
    .llseek     = seq_lseek,
    .release    = single_release,
};

