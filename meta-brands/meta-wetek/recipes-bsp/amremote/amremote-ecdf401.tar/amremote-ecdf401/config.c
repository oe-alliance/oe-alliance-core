#include <stdio.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include "remote_config.h"

#define PRINT_CONFIG

int set_config(remote_config_t *remote, int device_fd)
{
    unsigned int i;
    unsigned int *para=(unsigned int*)remote + 4;

    for(i = 0; i < ARRAY_SIZE(config_item); i++){
        if(para[i]!=0xffffffff){
#ifdef PRINT_CONFIG
            switch(i){
                case 4:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    printf("%20s = 0x%x\n", config_item[i], para[i]);
                    break;
                default:
                    printf("%20s = %d\n", config_item[i], para[i]);
                    break;
                }
#endif
            ioctl(device_fd, remote_ioc_table[i], &para[i]);
            }
        }
    return 0;
}
