/* fakestb - header file
	by infiniti 	- 2012

*/

#define PROCFS_MAX_SIZE		512

static struct proc_dir_entry* dir_stb;
static struct proc_dir_entry* dir_stb_info;
static struct proc_dir_entry* file_stb_info_model;

static struct proc_dir_entry* dir_stb_video;
static struct proc_dir_entry* file_stb_video_videomodechoices;
static struct proc_dir_entry* file_stb_video_aspectchoices;

static struct proc_dir_entry* dir_stb_denc;
static struct proc_dir_entry* dir_stb_denc0;


// test read/write
static struct proc_dir_entry* file_stb_video_aspect;
static char stb_video_aspect_buffer[PROCFS_MAX_SIZE];
static unsigned long stb_video_aspect_buffersize = 0;

static struct proc_dir_entry* file_stb_video_policy;
static char stb_video_policy_buffer[PROCFS_MAX_SIZE];
static unsigned long stb_video_policy_buffersize = 0;

static struct proc_dir_entry* file_stb_denc0_wss;
static char stb_denc0_wss_buffer[PROCFS_MAX_SIZE];
static unsigned long stb_denc0_wss_buffersize = 0;

/* more ?needed? proc_dir_entries ( dirs )
static struct proc_dir_entry* dir_stb_audio;
static struct proc_dir_entry* dir_stb_fp;

static struct proc_dir_entry* dir_stb_frontend;
static struct proc_dir_entry* dir_stb_vmpeg;
*/

// read-write entries
//static struct proc_dir_entry* file_stb_video_videomode;

static int stb_info_model_open(struct inode *inode, struct file *file);
static const struct file_operations stb_info_model_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_info_model_open,
    .read       = seq_read,
    .llseek     = seq_lseek,
    .release    = single_release,
};

static int stb_video_videomodechoices_open(struct inode *inode, struct file *file);
static const struct file_operations stb_video_videomodechoices_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_video_videomodechoices_open,
    .read       = seq_read,
    .llseek     = seq_lseek,
    .release    = single_release,
};

static int stb_video_aspectchoices_open(struct inode *inode, struct file *file);
static const struct file_operations stb_video_aspectchoices_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_video_aspectchoices_open,
    .read       = seq_read,
    .llseek     = seq_lseek,
    .release    = single_release,
};

// test read/write 
static int stb_video_aspect_open(struct inode *inode, struct file *file);
static ssize_t stb_video_aspect_write(struct file *file, const char *buffer, size_t len, loff_t * off);
static const struct file_operations stb_video_aspect_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_video_aspect_open,
    .read       = seq_read,
    .write	= stb_video_aspect_write, 
    .llseek     = seq_lseek,
    .release    = single_release,
};

static int stb_video_policy_open(struct inode *inode, struct file *file);
static ssize_t stb_video_policy_write(struct file *file, const char *buffer, size_t len, loff_t * off);
static const struct file_operations stb_video_policy_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_video_policy_open,
    .read       = seq_read,
    .write      = stb_video_policy_write,
    .llseek     = seq_lseek,
    .release    = single_release,
};

static int stb_denc0_wss_open(struct inode *inode, struct file *file);
static ssize_t stb_denc0_wss_write(struct file *file, const char *buffer, size_t len, loff_t * off);
static const struct file_operations stb_denc0_wss_fops = {
    .owner      = THIS_MODULE,
    .open       = stb_denc0_wss_open,
    .read       = seq_read,
    .write      = stb_denc0_wss_write,
    .llseek     = seq_lseek,
    .release    = single_release,
};

