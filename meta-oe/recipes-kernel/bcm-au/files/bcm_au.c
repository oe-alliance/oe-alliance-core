/*
 * "bcm_au"
 *
 */

#include <linux/fs.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include "aes256.h"

static const uint8_t IV[16]={0x86, 0xaf, 0xc4, 0x38, 0x68, 0xfe, 0xa6, 0xab, 0xd4, 0x0f, 0xbf, 0x6d, 0x5e, 0xd5, 0x09, 0x05};
static const uint8_t Key256[32]={0xf4, 0x15, 0x0d, 0x4a, 0x1a, 0xc5, 0x70, 0x8c, 0x29, 0xe4, 0x37, 0x74, 0x90, 0x45, 0xa3, 0x9a, 0x29, 0xe4, 0x37, 0x74, 0x1a, 0xc5, 0x70, 0x8c, 0xf4, 0x15, 0x0d, 0x4a, 0x90, 0x45, 0xa3, 0x9a};

static char buffer[256];

static aes256_context aes_ctx;

static int bcm_au_error;

static unsigned int _Base64_encode(unsigned int buf)
{
	unsigned int result = 0;
	unsigned char c;
	int triple_cnt;

	for(triple_cnt = 18; triple_cnt >= 0; triple_cnt -= 6)
	{
		result <<= 8;
		switch(c = ((buf >> triple_cnt) & 0x3F))
		{
			case 0 ... 25:
				c += 'A';
				break;
			case 26 ... 51:
				c += ('a' - 26);
				break;
			case 52 ... 61:
				c += ('0' - 52);
				break;
			case 62:
				c = '+';
				break;
			case 63:
				c = '/';
				break;
		}
		result |= c;
	}
	return result;
}

static int Base64_encode(unsigned char *source, unsigned char *dest, int len)
{
	int s_idx = 0;
	int d_idx = 0;
	unsigned int ByteBuffer;

	for(s_idx = 0; s_idx < len; s_idx += 3)
	{
		ByteBuffer = source[s_idx + 0];
		ByteBuffer <<= 8;
		if((len - s_idx) > 1) ByteBuffer |= source[s_idx + 1];
		ByteBuffer <<= 8;
		if((len - s_idx) > 2) ByteBuffer |= source[s_idx + 2];
		ByteBuffer = _Base64_encode(ByteBuffer);
		dest[d_idx++] = ByteBuffer >> 24;
		dest[d_idx++] = ByteBuffer >> 16;
		if((len - s_idx) > 1) dest[d_idx++] = ByteBuffer >> 8;
		if((len - s_idx) > 2) dest[d_idx++] = ByteBuffer;
	}
	dest[d_idx++] = '=';
	dest[d_idx] = '\0';

	return d_idx;
}

static int Base64_decode(char * source, char *dest)
{
	int triple_cnt = 0;
	int s_idx = 0;
	int d_idx = 0;
	char c;
	unsigned int ByteBuffer;

	while((c = source[s_idx++]) != '=')
	{
		triple_cnt++;
		ByteBuffer <<= 6;
		switch(c)
		{
			case 'A'...'Z':
				ByteBuffer += (c - 'A');
				break;
			case 'a'...'z':
				ByteBuffer += (c - 'a') + 26;
				break;
			case '0'...'9':
				ByteBuffer += (c - '0') + 52;
				break;
			case '+':
				ByteBuffer += 62;
				break;
			case '/':
				ByteBuffer += 63;
				break;
			default:
			{
				triple_cnt--;
				ByteBuffer >>= 6;
			}
		}
		if(triple_cnt == 4)
		{
			dest[d_idx++] = ByteBuffer >> 16;
			dest[d_idx++] = ByteBuffer >> 8;
			dest[d_idx++] = ByteBuffer;
			triple_cnt = 0;
		}
	}
	switch(triple_cnt)
	{
		case 3:
			dest[d_idx++] = ByteBuffer >> 10;
			dest[d_idx++] = ByteBuffer >> 2;
			break;
		case 2:
			dest[d_idx++] = ByteBuffer >> 4;
			break;
		default:
		{}
	}
	return d_idx;
}

static void aes_decrypt(unsigned char *crypt, unsigned char *vector, unsigned char *key, int len)
{
	int offs;
	aes256_init_ex(&aes_ctx, Key256, vector);
	for(offs = 0; offs < len; offs += 16) aes256_decrypt_cbc(&aes_ctx, crypt + offs);
}

static void aes_encrypt(unsigned char *plain, unsigned char *vector, unsigned char *key, int len)
{
	int offs;
	aes256_init_ex(&aes_ctx, Key256, vector);
	for(offs = 0; offs < len; offs += 16) aes256_encrypt_cbc(&aes_ctx, plain + offs);
}

static long bcm_au_ioctl(struct file *instanz, unsigned int cmd, unsigned long arg)
{
	switch( cmd ) 
	{
		case 0x76:
		{
			break;
		}
		case 0x77:
		{
			break;
		}
		case 0x78:
		{
			Base64_decode(*((char**)(arg+4)), buffer);
			aes_decrypt(buffer, IV, Key256, 128);

			((uint16_t*)buffer)[0x1e] = ((uint16_t*)buffer)[0x00];
			((uint16_t*)buffer)[0x00] = ((uint16_t*)buffer)[0x01];
			((uint16_t*)buffer)[0x01] = ((uint16_t*)buffer)[0x1d];
			((uint16_t*)buffer)[0x0a] = ((uint16_t*)buffer)[0x0a];
			((uint16_t*)buffer)[0x02] = ((uint16_t*)buffer)[0x15];
			
			aes_encrypt(buffer, IV, Key256, 128);
			Base64_encode(buffer,*((char**)(arg+4)), 128);
			break;
		}
		default:
		return -EINVAL;
	}
	return 0;
}

static int bcm_au_open(struct inode *inode, struct file *file)
{
	return 0;
}

static int bcm_au_release(struct inode *inode, struct file *file)
{
	return 0;
}

static const struct file_operations bcm_au_private_fops = {
	.owner		= THIS_MODULE,
	.open		= bcm_au_open,
	.release	= bcm_au_release,
	.unlocked_ioctl	= bcm_au_ioctl,
};

static struct miscdevice bcm_au_dev = {
	MISC_DYNAMIC_MINOR,
	"bcm_au",
	&bcm_au_private_fops
};

static int __init bcm_au_init(void)
{
	int ret;

	ret = misc_register(&bcm_au_dev);
	return ret;
}

static void __exit bcm_au_exit(void)
{
	misc_deregister(&bcm_au_dev);
}

module_init(bcm_au_init);
module_exit(bcm_au_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("");
MODULE_DESCRIPTION("bcm_au_emu");
MODULE_VERSION("20151226");
