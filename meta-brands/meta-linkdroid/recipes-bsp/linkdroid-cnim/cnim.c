/*

Creates E2 compatible NIM sockets in /tmp/NIMS
use  cat /tmp/NIMS > /proc/bus/nim_sockets  before E2 starts

TODO:
a) multi mode tuners are probably not scanned proprerly
check Componenents/NimSockets.py enumerateNIMs  for Mode: multi_type

NIM Socket 0:
Type: DVB-C
Name: Vuplus DVB-C NIM(CXD1978)
Mode 0: DVB-C
Mode 1: DVB-T
Frontend_Device: 0
I2C_Device: 2

NIM Socket 1:
Type: DVB-C
Name: Vuplus DVB-C NIM(CXD1978)
Mode 0: DVB-C
Mode 1: DVB-T
Frontend_Device: 1
I2C_Device: 3


b) some brcm drivers seem to insert Multistream, NOT in E2 code check

c) need to check expand mode types via DTV_ENUM_DELSYS as:

struct dvb_frontend_info {
	char       name[128];
	fe_type_t  type;			// DEPRECATED. Use DTV_ENUM_DELSYS instead
	__u32      frequency_min;
	__u32      frequency_max;
	__u32      frequency_stepsize;
	__u32      frequency_tolerance;
	__u32      symbol_rate_min;
	__u32      symbol_rate_max;
	__u32      symbol_rate_tolerance;	// ppm
	__u32      notifier_delay;		// DEPRECATED
	fe_caps_t  caps;
};

*/


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/dvb/frontend.h>


int PrintHelp () {
    printf ("\nCommand line options:\n \
    Optional:\n \
	-a number of adapter     : provide a numerical value default:4\n \
	-f number of frontend    : provide a numerical default:4\n \
	-o /path/filename        : path and filename default:./nim_sockets\n\
	-h                       : this help\n \
	-d                       : enable debug\n");
}

int main(int argc, char **argv) {

  char *ArrayDVBTunerType[] = { "UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN"};
  char **DVBTunerType;
  char NIM_File[254];
  short TunerFound = 0;
  short OutfileOK = 0;
  short Debug = 1;
  int c = -1;
  int FE_Max = 4;
  int Adaptor_Max = 4;
  FILE *outfile;

//  extern char *optarg;
//  extern int optind, opterr, optopt;

  strcpy (NIM_File, "/tmp/NIMS");

  printf("\nInfo: create_nim_sockets started\n\n");

   while((c = getopt(argc, argv, "a:f:o:dh")) != -1) {
    switch(c) {
    case 'd': // enable debug
     Debug = 1;

    break;

    case 'h': // help
      PrintHelp();
      return -1;
      break;

      case 'a':
             if ( atoi(optarg) )
               Adaptor_Max = atoi(optarg);
             else
	     {
               fprintf (stderr, "Error: Invalid parameter for -a should be a number.\n");
	       return -1;
	     }

	     break;
      case 'f':
             if ( atoi(optarg) )
               FE_Max = atoi(optarg);
             else
	     {
               fprintf (stderr, "Error: Invalid parameter for -f should be a number.\n");
	      return -1;
	     }

	     break;
	case 'o':
	      strcpy(NIM_File, optarg);
	     break;

        default:
               PrintHelp();
             return -1;
	     break;
    }
   }


  if (optind != argc ) {
    fprintf (stderr, "Error: unknow parameter detected [%s].\n", argv[argc-1]);
    PrintHelp();
    return -1;
  }



  if (outfile = fopen(NIM_File, "w"))
	{
	OutfileOK = 1;
	}
	else
	{
		printf("\nError creating nim_sockets file [%s]\n", NIM_File);
		return -1;
	}



  DVBTunerType = ArrayDVBTunerType;
  int TunerType_S = 0;
  int TunerType_C = 1;
  int TunerType_T = 2;
  int TunerType_A = 3;
  DVBTunerType[TunerType_S]="DVB-S";
  DVBTunerType[TunerType_C]="DVB-C";
  DVBTunerType[TunerType_T]="DVB-T";
  DVBTunerType[TunerType_A]="ATSC";
  /* DVB-S2 is a special case of DVB-S and is tested from frontend capabilities */





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
	    printf("Tuner found: %s: %s\n", devstr, fe_info.name);
		if (1) {
				printf("// dvb_frontend_info for %s\n", devstr);
				printf("struct dvb_frontend_info FETYPE = {\n");
				printf("  .name                  = \"%s\",\n", fe_info.name);
				printf("  .type                  = %d,\n", fe_info.type);
				printf("  .frequency_min         = %d,\n", fe_info.frequency_min);
				printf("  .frequency_max         = %d,\n", fe_info.frequency_max);
				printf("  .frequency_stepsize    = %d,\n", fe_info.frequency_stepsize);
				printf("  .frequency_tolerance   = %d,\n", fe_info.frequency_tolerance);
				printf("  .symbol_rate_min       = %d,\n", fe_info.symbol_rate_min);
				printf("  .symbol_rate_max       = %d,\n", fe_info.symbol_rate_max);
				printf("  .symbol_rate_tolerance = %d,\n", fe_info.symbol_rate_tolerance);
				printf("  .notifier_delay        = %d,\n", fe_info.notifier_delay);
				printf("  .caps                  = 0x%x\n", fe_info.caps);
				if  ( (fe_info.caps & FE_CAN_2G_MODULATION ) == FE_CAN_2G_MODULATION )
		 			printf("  2nd gen modulation     = true\n");
				if  ( (fe_info.caps & FE_HAS_EXTENDED_CAPS ) ==  FE_HAS_EXTENDED_CAPS )
					printf("  has extended caps      = true\n");
				if  ( (fe_info.caps & FE_CAN_MULTISTREAM ) ==  FE_CAN_MULTISTREAM )
					printf("  multistream filter      = true\n");
				if  ( (fe_info.caps & FE_CAN_TURBO_FEC ) ==  FE_CAN_TURBO_FEC )
					printf("  turbo fec modulation    = true\n");
				printf("};\n\n");
				}

/* Nim socket outpout start */
	//	if (Debug) printf("NIM Socket %d:\n", i);
	//	if (OutfileOK) { fprintf(outfile, "NIM Socket %d:\n", i); }
		if (1) printf("NIM Socket %d:\n", j);
		if (OutfileOK) { fprintf(outfile, "NIM Socket %d:\n", j); }
		if ( (fe_info.caps & FE_CAN_2G_MODULATION ) == FE_CAN_2G_MODULATION ) {
			/* 2nd generation DVB Tuner detected adding 2 to the TunerType */
			if (1) printf("      Type: %s2\n", DVBTunerType[fe_info.type]);
			if (OutfileOK) { fprintf(outfile,"      Type: %s2\n", DVBTunerType[fe_info.type]); }
			}
		else {
			/* 1st generation DVB Tuner */
			if (1) printf("      Type: %s\n", DVBTunerType[fe_info.type]);
			if (OutfileOK) { fprintf(outfile,"      Type: %s\n", DVBTunerType[fe_info.type]);}
				}
			if (1) {
				    printf("      Name: %s\n", fe_info.name);
				    printf("      Has_Outputs: no\n");
				    printf("      Frontend_Device: %d\n", j);
				    printf("\n");
			}
		if (OutfileOK) {
			fprintf(outfile,"      Name: %s\n", fe_info.name);
			fprintf(outfile,"      Has_Outputs: no\n");
			//fprintf(outfile,"      Frontend_Device: %d\n", i);
			fprintf(outfile,"      Frontend_Device: %d\n", j);
			fprintf(outfile,"\n");}
/* Nim socket output end */

	}
	else
	{
	  fprintf (stderr, "Error: ioctl error [%d] for [%s] : ", Return_ioctl, devstr);
	}
      }

      close( fe_fd );
    }
    else {
    // file doesn't exist
    if (1)	printf("/dev/dvb/adapter%d/frontend%d doesn\'t exist\n",i,j);
   }
  }
  }
  if (!TunerFound) { printf("\n\nError: Unable to find a valid DVB card or vtuner on your system.\n\
    Please fix this problem and run it again.\n"); }
  if (OutfileOK) {
    fclose(outfile);
    if (TunerFound) { printf("\nInfo: File [%s] created please check it.\n", NIM_File);
		    }
		}
  printf("\nInfo: create_nim_sockets done\n");
}
