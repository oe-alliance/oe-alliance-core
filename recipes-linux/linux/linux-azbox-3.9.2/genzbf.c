
/*****************************************
 Copyright (c) 2002-2008
 Sigma Designs, Inc. All Rights Reserved
 Proprietary and Confidential
 *****************************************/

/*
 * Utility program to generate zboot header prepend file.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdint.h>
#include <fcntl.h>
#include <ctype.h>
#include <string.h>
#include <sys/stat.h>

/* Get the definition needed for the header */
#include "zboot.h"

/* Globals */
static const char zboot_fsig[4] = { 'F', 'N', 'I', 'B' }; /* "BINF" */
static struct zboot_fhdr fhdr;
static struct stat stat_buf;
static char *ifname = NULL, *ofname = NULL;
static int inf, outf;

/* Target always little endian, so if host is big endian, swap is needed */
static int swap = 0;	

/* Convert a numeric ASCII string to uint32_t number */
static uint32_t string2hex(const char *str, uint32_t *lptr)
{
  int mode;

  /* Need to starts with "0x" or "0X" for hex */
  if ((*str == '0') && (toupper(*(str + 1)) == 'X')) {
    mode = 16; /* hex */
    str += 2;
  } else if (isdigit(*str)) 
    mode = 10; /* decimal */
  else
    return(-1);
  
  *lptr = 0;
  for (; *str != '\0'; str++) {
    if (isdigit(*str)) 
      *lptr = ((*lptr) * mode) + (*str - '0');
    else if (isxdigit(*str)) {
      *lptr = ((*lptr) * mode) + (toupper(*str) - 'A' + 10);
    } else
      return(-1);
  }
  return(0);
}

static inline uint32_t get_dword_from_buf(unsigned char *buf)
{
  uint32_t val = 0;
  if (swap != 0) 
    val = buf[3] | (buf[2] << 8) | (buf[1] << 16) | (buf[0] << 24); 
  else
    val = buf[0] | (buf[1] << 8) | (buf[2] << 16) | (buf[3] << 24); 
  return val;
}

/* Check the endian-ness of the host: 0=little, 1=big */
static int endian(void)
{
  union {
    uint32_t l;
    unsigned char c[sizeof(uint32_t)];
  } u;

  u.l = 0x12345678;
  return((u.c[0] == 0x12) ? 1 : 0);
}

static void swap_endian(void *src, unsigned size)
{
  unsigned char *c = (unsigned char *)src;
  unsigned char *e = c + size - 1;
  unsigned char tmp;

  if (size <= 1)
    return; /* No need for swapping */

  for (; c < e; c++, e--) {
    tmp = *c;
    *c = *e;
    *e = tmp;
  }
}

static int checksum(int fno, uint32_t *lptr, uint32_t offset)
{
  uint32_t sum = 0;
  uint32_t i, size;
  unsigned char *ptr;
  uint32_t l;
  
  if (lseek(fno, offset, SEEK_SET) < 0)
    return(-1);
  
  for (; (size = read(fno, &l, sizeof(uint32_t))) == sizeof(uint32_t);) 
    sum += get_dword_from_buf((unsigned char *)&l);

  if (size & 0x3) {
    union {
      unsigned char c[4];
      uint32_t l;
    } buf;

    buf.l = 0;
    for (i = 0, ptr = (unsigned char *)&l; i < (size & 0x3); i++) 
      buf.c[i] = *ptr++;
    sum += get_dword_from_buf(&buf.c[0]);
  }	    
  *lptr = sum;
  return(0);
}

/* Copy file */
static int copyfile(int ofno, int ifno, off_t offset)
{
  unsigned char c;
  
  /* From the beginning of the source */
  if (lseek(ifno, offset, SEEK_SET) < 0)
    return(-1);
  
  for (; read(ifno, &c, sizeof(unsigned char)) > 0;) {
    if (write(ofno, &c, sizeof(unsigned char)) != sizeof(unsigned char))
       return(-1);
  }
  return(0);
}

int main(int argc, char *argv[])
{
  int i, modify = 0;
  char *ptr;
	
  memset(&fhdr, 0, sizeof(struct zboot_fhdr));
  memcpy(&fhdr.signature, zboot_fsig, 4);
  
  if (argc <= 1)
    goto usage;
  else
    swap = endian();
	
  /* Check and setup options */
  for (i = 1; i < argc; i++) {
    if (*(argv[i]) != '-') {
      ifname = argv[i];
    } else if (strncmp(argv[i], "-o", 2) == 0) {
      if (++i >= argc)
	goto usage;
      ofname = argv[i];
    } else if (strncmp(argv[i], "-z", 2) == 0) {
      if (++i >= argc)
	goto usage;
      ifname = argv[i];
      modify = 1;
    } else if (strncmp(argv[i], "-l", 2) == 0) {
      if (++i >= argc)
	goto usage;
      if (string2hex(argv[i], &fhdr.loadaddr) != 0)
	goto usage;
    } else if (strncmp(argv[i], "-s", 2) == 0) {
      if (++i >= argc)
	goto usage;
      if (string2hex(argv[i], &fhdr.startaddr) != 0)
	goto usage;
    } else if (strncmp(argv[i], "-e", 2) == 0) {
      if (++i >= argc)
	goto usage;
      if (string2hex(argv[i], &fhdr.enc_hdrsize) != 0)
	goto usage;
    } else if (strncmp(argv[i], "-a", 2) == 0) {
      if (++i >= argc)
	goto usage;
      ptr = argv[i];
      for (; *ptr != '\0'; ptr++) {
	switch(*ptr) {
	case 'l':
	  fhdr.attributes |= ZBOOT_ATTR_LOAD;	
	  break;
	case 'e':
	  fhdr.attributes |= ZBOOT_ATTR_ENCRYPT;	
	  break;
	case 'z':
	  fhdr.attributes |= ZBOOT_ATTR_GZIP;	
	  break;
	case 'x':
	  fhdr.attributes |= ZBOOT_ATTR_EXEC;	
	  break;
	case 'f':
	  fhdr.attributes |= ZBOOT_ATTR_FINAL;	
	  break;
	case 's':
	  fhdr.attributes |= ZBOOT_ATTR_FINAL_SESS;
	  break;
	case 'p':
	  fhdr.attributes |= ZBOOT_ATTR_PHY_LOAD;
	  break;
	default:
	  printf("wrong attribute: l, e, x, z, f, s, p\n");
	  goto error;
	}			
      }
    } else {
	printf("Unknown option (%s)\n", argv[i]);
	goto usage;
    }
  }

  if (modify) {
    if (fhdr.attributes & ZBOOT_ATTR_GZIP)
	goto usage;
  }

  fhdr.version = ZBOOT_VERSION;

  if ((ifname == NULL) || (ofname == NULL))
    goto usage;
  else if ((inf = open(ifname, O_RDONLY)) < 0) {
    printf("open %s failed.\n", ifname);
    goto error;
  } else if ((outf = open(ofname, O_CREAT | O_WRONLY | O_TRUNC, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH)) < 0) {
    close(inf);
    printf("open %s failed.\n", ofname);
    goto error;
  } else if (fstat(inf, &stat_buf) < 0) {
    close(inf);
    printf("stat on %s failed.\n", ofname);
    goto error;
  } 
  
  fhdr.size = stat_buf.st_size;

  if (modify) 
    fhdr.size -= sizeof(struct zboot_fhdr);

  if (checksum(inf, &fhdr.checksum, modify ? sizeof(struct zboot_fhdr) : 0) < 0) {
    close(inf);
    close(outf);
    printf("reading %s failed\n", ifname);
    goto error;
  } 

  if (swap != 0) {
    swap_endian(&fhdr.signature, sizeof(fhdr.signature));
    swap_endian(&fhdr.loadaddr, sizeof(fhdr.loadaddr));
    swap_endian(&fhdr.startaddr, sizeof(fhdr.startaddr));
    swap_endian(&fhdr.attributes, sizeof(fhdr.attributes));
    swap_endian(&fhdr.checksum, sizeof(fhdr.checksum));
    swap_endian(&fhdr.version, sizeof(fhdr.version));
    swap_endian(&fhdr.size, sizeof(fhdr.size));
    swap_endian(&fhdr.enc_hdrsize, sizeof(fhdr.enc_hdrsize));
  }

  if (write(outf, &fhdr, sizeof(struct zboot_fhdr)) != 
	     sizeof(struct zboot_fhdr)) {
    close(inf);
    close(outf);
    printf("writing %s failed\n", ofname);
    goto error;
  } else if (copyfile(outf, inf, modify ? sizeof(struct zboot_fhdr) : 0) < 0) {
    close(inf);
    close(outf);
    printf("writing %s failed\n", ofname);
    goto error;
  }
  
  close(inf);
  close(outf);

  chmod(ofname, 0644);
  
  printf("%szboot file header:\n", modify ? "modified " : "");
  printf("\tHost: %s endian\n", (swap != 0) ? "big" : "little");
  printf("\tsignature: 0x%08x\n", fhdr.signature);
  printf("\tloadaddr: 0x%08x\n", fhdr.loadaddr);
  printf("\tstartaddr: 0x%08x\n", fhdr.startaddr);
  printf("\tattribute: 0x%08x\n", fhdr.attributes);
  printf("\tchecksum: 0x%08x\n", fhdr.checksum);
  printf("\tenc_hdrsize: 0x%08x %s\n", fhdr.enc_hdrsize, fhdr.enc_hdrsize == 0 ? "(use default)" : "");
  printf("\tversion: 0x%08x\n", fhdr.version);
  printf("\tsize: 0x%08x\n", fhdr.size);
  return(0);
  
usage:
  printf("usage: %s -l load_addr -s start_addr -a lexzfs -e enc_hdr_size -o outfile infile\n", argv[0]);
  printf("\tattribute: l=load\n");
  printf("\t           e=encrypted (def. enc_hder_size=2KB if not given)\n");
  printf("\t           x=execute\n");
  printf("\t           z=gzipped content\n");
  printf("\t           p=physical load address given\n");
  printf("\t           f=cont_final (final for container)\n");
  printf("\t           s=sess_final (final for session -- possibly across containers)\n");
  printf("usage: %s -l load_addr -s start_addr -a lexfs -e enc_hdr_size -o outfile -z infile\n", argv[0]);
  printf("\t           -z modify existing zbf file\n");

error:
  return(1);
}

