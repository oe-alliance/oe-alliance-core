/* fakestb - main module
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


#include "stb-procfs.h"


// info/model
static int 
stb_info_model_show(struct seq_file *m, void *v)
{
    seq_printf(m, "amlogic\n");
    return 0;
}

static int
stb_info_model_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_info_model_show, NULL);
}

// video/choices
static int
stb_video_videomodechoices_show(struct seq_file *m, void *v)
{
    seq_printf(m, "1080p 1080i 720p\n");
    return 0;
}
static int
stb_video_videomodechoices_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_video_videomodechoices_show, NULL);
}

static int
stb_video_aspectchoices_show(struct seq_file *m, void *v)
{
    seq_printf(m, "any 4:3 16:9\n");
    return 0;
}
static int
stb_video_aspectchoices_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_video_aspectchoices_show, NULL);
}

// test read/write
static int
stb_video_aspect_show(struct seq_file *m, void *v)
{
    seq_printf(m, stb_video_aspect_buffer);
    return 0;
}
static int
stb_video_aspect_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_video_aspect_show, NULL);
}

static ssize_t
stb_video_aspect_write(struct file *file, const char *buffer, size_t len, loff_t * off) 
{
    if ( len > PROCFS_MAX_SIZE ) {
	stb_video_aspect_buffersize = PROCFS_MAX_SIZE;
    } else {
	stb_video_aspect_buffersize = len;
    }
  
    if ( copy_from_user(stb_video_aspect_buffer, buffer, stb_video_aspect_buffersize) ) {
	return -EFAULT;
    }
    return stb_video_aspect_buffersize;
}

static int
stb_video_policy_show(struct seq_file *m, void *v)
{
    seq_printf(m, stb_video_policy_buffer);
    return 0;
}
static int
stb_video_policy_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_video_policy_show, NULL);
}

static ssize_t
stb_video_policy_write(struct file *file, const char *buffer, size_t len, loff_t * off)
{
    if ( len > PROCFS_MAX_SIZE ) {
        stb_video_policy_buffersize = PROCFS_MAX_SIZE;
    } else {
        stb_video_policy_buffersize = len;
    }
 
    if ( copy_from_user(stb_video_policy_buffer, buffer, stb_video_policy_buffersize) ) {
        return -EFAULT;
    }
    return stb_video_policy_buffersize;
}

static int
stb_denc0_wss_show(struct seq_file *m, void *v)
{
    seq_printf(m, stb_denc0_wss_buffer);
    return 0;
}
static int
stb_denc0_wss_open(struct inode *inode, struct file *file)
{
    return single_open(file, stb_denc0_wss_show, NULL);
}

static ssize_t
stb_denc0_wss_write(struct file *file, const char *buffer, size_t len, loff_t * off)
{
    if ( len > PROCFS_MAX_SIZE ) {
        stb_denc0_wss_buffersize = PROCFS_MAX_SIZE;
    } else {
        stb_denc0_wss_buffersize = len;
    }

    if ( copy_from_user(stb_denc0_wss_buffer, buffer, stb_denc0_wss_buffersize) ) {
        return -EFAULT;
    }
    return stb_denc0_wss_buffersize;
}

static int __init 
stb_init(void)
{
    // main stb entry
    dir_stb = proc_mkdir("stb",NULL);
    if (!dir_stb) {
        return -ENOMEM;
    }

    // info dir
    dir_stb_info = proc_mkdir("info",dir_stb);
    if (!dir_stb_info) {
        return -ENOMEM;
    }
    file_stb_info_model = proc_create("model", 0, dir_stb_info, &stb_info_model_fops);
    if (!file_stb_info_model) {
        return -ENOMEM;
    }

    // video dir
    dir_stb_video = proc_mkdir("video",dir_stb);
    if (!dir_stb_video) {
        return -ENOMEM;
    }   
    file_stb_video_videomodechoices = proc_create("videomode_choices", 0, dir_stb_video, 
		&stb_video_videomodechoices_fops);
    if (!file_stb_video_videomodechoices) { return -ENOMEM; }   
    file_stb_video_aspectchoices = proc_create("aspect_choices", 0, dir_stb_video,
                &stb_video_aspectchoices_fops);
    if (!file_stb_video_aspectchoices) { return -ENOMEM; }

    // test read/write
    file_stb_video_aspect = proc_create("aspect", (S_IRUGO|S_IWUGO) , dir_stb_video,
                &stb_video_aspect_fops);
    if (!file_stb_video_aspect) { return -ENOMEM; } 
    else { 
	stb_video_aspect_buffersize=5;
	memcpy(stb_video_aspect_buffer,"16:9\n",stb_video_aspect_buffersize); 
    }
    file_stb_video_policy = proc_create("policy", (S_IRUGO|S_IWUGO) , dir_stb_video,
                &stb_video_policy_fops);
    if (!file_stb_video_policy) { return -ENOMEM; }
    else {
        stb_video_policy_buffersize=8;
        memcpy(stb_video_policy_buffer,"bestfit\n",stb_video_policy_buffersize);
    }

    // denc dir
    dir_stb_denc = proc_mkdir("denc",dir_stb);
    if (!dir_stb_denc) {
        return -ENOMEM;
    }  
    dir_stb_denc0 = proc_mkdir("0",dir_stb_denc);
    if (!dir_stb_denc0) {
        return -ENOMEM;
    }  
    file_stb_denc0_wss = proc_create("wss", (S_IRUGO|S_IWUGO) , dir_stb_denc0,
                &stb_denc0_wss_fops);
    if (!file_stb_denc0_wss) { return -ENOMEM; }
    else {
        stb_denc0_wss_buffersize=5;
        memcpy(stb_denc0_wss_buffer,"auto\n",stb_denc0_wss_buffersize);
    }



    return 0;
}

static void __exit
stb_exit(void)
{
    // remove denc
    remove_proc_entry("wss", dir_stb_denc0);
    remove_proc_entry("0",dir_stb_denc);
    remove_proc_entry("denc",dir_stb);

    // remove video
    remove_proc_entry("policy", dir_stb_video);
    remove_proc_entry("aspect", dir_stb_video);
    remove_proc_entry("aspect_choices", dir_stb_video);
    remove_proc_entry("videomode_choices", dir_stb_video);
    remove_proc_entry("video",dir_stb);

    // remove info   
    remove_proc_entry("model", dir_stb_info);
    remove_proc_entry("info",dir_stb);

    remove_proc_entry("stb",NULL);
}

module_init(stb_init);
module_exit(stb_exit);

MODULE_LICENSE("GPL"); 
