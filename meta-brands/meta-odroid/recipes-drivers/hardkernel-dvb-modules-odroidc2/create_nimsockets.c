
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/ioctl.h>


#include <linux/dvb/frontend.h>

const char *tmpl = "NIM Socket %d:\n\tType: %s\n\tName: %s\n\tHas_Outputs: no\n\tFrontend_Device: %d\n";
char out[2048];

int main() {
    char *ArrayDVBTunerType[] = { "UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN"};
    char **DVBTunerType;

    DVBTunerType = ArrayDVBTunerType;
    int TunerType_S = 0;
    int TunerType_C = 1;
    int TunerType_T = 2;
    DVBTunerType[TunerType_S]="DVB-S";
    DVBTunerType[TunerType_C]="DVB-C";
    DVBTunerType[TunerType_T]="DVB-T";
    char ttype[8];
    char nimentry[256];

    int FE_Max = 4;
    int Adaptor_Max = 4;
    short TunerFound = 0;

    int i=0;
    int j=0;
    int Return_ioctl=0;

    for(i=0; i<Adaptor_Max; ++i) {
        for (j=0; j<FE_Max; ++j) {
            char devstr[80];
            sprintf( devstr, "/dev/dvb/adapter%d/frontend%d", i, j);
            if( access( devstr, F_OK ) != -1 ) {
                //file exists
                int fe_fd = open( devstr, O_RDONLY);
                if(fe_fd>0) {
                    struct dvb_frontend_info fe_info;
                    Return_ioctl = ioctl(fe_fd, FE_GET_INFO, &fe_info);
                    if(Return_ioctl == 0) {
                        TunerFound=1;
                        //printf("Tuner found: %s: %s\n", devstr, fe_info.name);
                        if ( (fe_info.caps & FE_CAN_2G_MODULATION ) == FE_CAN_2G_MODULATION ) {
                            /* 2nd generation DVB Tuner detected adding 2 to the TunerType */
                            sprintf(ttype,"%s2", DVBTunerType[fe_info.type]);
                        } else {
                            /* 1st generation DVB Tuner */
                            sprintf(ttype,"%s", DVBTunerType[fe_info.type]);
                        }
                        sprintf(nimentry,tmpl,i,ttype,fe_info.name,j);
			strcat(out,nimentry);
                    }
                }
            }
        }
    }
    printf(out);
    int fd=open("/proc/bus/nim_sockets",O_WRONLY);
    if (fd<0){ return -1; }
    else {
	write(fd,out,sizeof(out));
	close(fd);
    }

}
