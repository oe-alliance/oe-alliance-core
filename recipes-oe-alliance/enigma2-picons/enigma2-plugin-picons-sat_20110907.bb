# NOTE: Picon packages can have conflicting files, everything is installed
# in the same directory.
# For the satellites, servicerefs will never be the same. And we prepend
# satpos_provider_ before every servicename, so the png filenames also will
# never conflict.
# So we can safely list all SATNAMES here, and create all their piconsets
# from the same bb file.

PR = "r2"

SRC_URI = "http://downloads.pli-images.org/picons/picons-sat-${PV}.tar.gz"

PICON_SATNAMES = "4.8e 7.0e 9.0e 10.0e 13.0e 15.8e 16.0e 19.2e 23.5e 26.0e 28.2e 36.0e 39.0e 42.0e 45.0e \
  		  56.0e 57.0e 60.0e 68.5e 68.6e 74.0e 75.0e 78.5e 83.0e 85.2e 88.0e 91.4e 91.5e 95.0e 100.5e \
		  105.5e 108.2e 113.0e 115.5e 116.0e 122.2e 124.0e 128.0e 132.0e 134.0e 138.0e 140.0e 144.0e 152.0e 156.0e \
		  160.0e 180.0e 0.8w 4.0w 5.0w 7.0w 22.0w 30.0w 34.5w 61.0w 61.5w 65.0w 70.0w 72.7w 77.0w \
		  77.2w 82.0w 91.0w 110.0w 119.0w 121.0w 129.0w \
		 "

include enigma2-plugin-picons.inc

SRC_URI[md5sum] = "af4e5944c2c16d3f7f011dc57425f30e"
SRC_URI[sha256sum] = "611dcf751bc573c2aedd12a7f074e57afc939fe34bf30701eec83bd5d2635e38"
