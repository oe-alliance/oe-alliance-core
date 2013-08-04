# NOTE: Picon packages can have conflicting files, everything is installed
# in the same directory.
# For the satellites, servicerefs will never be the same. And we prepend
# satpos_provider_ before every servicename, so the png filenames also will
# never conflict.
# So we can safely list all SATNAMES here, and create all their piconsets
# from the same bb file.

PR = "r2"

SRC_URI = "http://downloads.pli-images.org/picons/picons-sat-${PV}.tar.gz"

PICON_SATNAMES = "4.8e 4.9e 5.0e 7.0e 9.0e 10.0e 13.0e 16.0e 19.2e 23.5e 26.0e 31.5e 35.9e 36.0e 39.0e \
		  42.0e 45.0e 55.8e 57.0e 60.0e 68.5e 75.0e 78.5e 83.0e 85.1e 88.0e 91.4e 91.5e 95.0e 100.5e \
		  105.5e 108.2e 113.0e 115.5e 116.0e 122.2e 124.0e 125.0e 128.0e 132.0e 134.0e 138.0e 140.0e 156.0e 160.0e \
		  166.0e 180.0e 0.8w 4.0w 5.0w 7.0w 30.0w 34.5w 65.0w 70.0w 72.7w 77.2w 129.0w \ 
		 "

include enigma2-plugin-picons.inc

SRC_URI[md5sum] = "43ebf736727b74a5eb1ecd84000a4828"
SRC_URI[sha256sum] = "38732bc9bc0c159cede7ef85810b8ba929791ccd7f7080177567ef1150d6b35c"
