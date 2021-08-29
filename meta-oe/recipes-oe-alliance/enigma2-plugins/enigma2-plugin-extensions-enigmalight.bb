DESCRIPTION = "An Ambilight clone for broadcom based linux receivers."
HOMEPAGE = "https://github.com/nickersk/enigmalight"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=156f94b2a67a05ad45ff99bec65b2c81"

DEPENDS += "libusb1"
RRECOMMENDS_${PN} = "python-cheetah libusb1 kernel-module-cdc-acm kernel-module-ftdi-sio kernel-module-usbserial kernel-module-ch341 curl"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.42+git${SRCPV}"
PKGV = "1.42+git${GITPKGV}"
PR = "r11"

SRC_URI = "git://github.com/nickersk/enigmalight.git;protocol=https;branch=4.4"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

do_install_append() {
    cd ${S}
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions
    cp -R ${S}/python/plugin/EnigmaLight ${D}${libdir}/enigma2/python/Plugins/Extensions

    install -d ${D}/home/elight-addons
    cp -R ${WORKDIR}/git/elight-addons/config_samples ${D}/home/elight-addons

    install -d ${D}/home/elight-addons/profiles_empty
    cp ${S}/python/plugin/EnigmaLight/profiles/* ${D}/home/elight-addons/profiles_empty

    install -d ${D}/home/elight-addons/wifilight
    cp -R ${WORKDIR}/git/elight-addons/wifilight ${D}/home/elight-addons/
    cp -R ${WORKDIR}/git/elight-addons/config_samples ${D}/home/elight-addons/wifilight
}

FILES_${PN} += "${libdir}/enigma2/python/Plugins/Extensions/EnigmaLight/ \
                /home/elight-addons"

pkg_preinst_${PN}() {
#/bin/sh

FILE=/etc/enigmalight.conf
FILE2=/usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight/profiles/custom_0.profile

mkdir -p /home/elight-addons > /dev/null
mkdir -p /home/elight-addons/profiles > /dev/null

echo ""
echo "Check if enigmalight is running..."
echo ""

EPID=`pidof enigmalight|head -n1`

if [ ${EPID} ]; then
	echo "enigmalight: running with PID ${EPID}, Exit..."
	kill -9 ${EPID} 2>/dev/null
else
	echo "enigmalight: not running"
fi

rm -rf /usr/bin/enigmalight 2>/dev/null
rm -rf /usr/bin/elighttalk 2>/dev/null
rm -rf /usr/bin/elightprefix 2>/dev/null

echo "Checking for $FILE..."
if [ -f $FILE ]; then
	echo "File $FILE exists"
	cp -f /etc/enigmalight.conf /home/elight-addons/enigmalight.conf
	echo "Backup created in /home/elight-addons"
else
   echo "File $FILE does not exist in /etc/"
fi

echo "Create backups from profiles..."
if [ -f $FILE2 ]; then
	echo "Profiles found, backup created in /home/elight-addons/profiles"
	cp -f /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight/profiles/*.profile /home/elight-addons/profiles/
else
	echo "No profiles found..."
fi

echo "Checking for an older version of EnigmaLight..."
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight ]
then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight > /dev/null 2>&1
	echo "An older version of EnigmaLight was found and removed"
else
	echo "EnigmaLight was not found in the system"
	echo "Proceeding to installation..."
fi
}

pkg_postinst_${PN}() {
#!/bin/sh

FILE=/home/elight-addons/enigmalight.conf
FILE2=/home/elight-addons/profiles/custom_0.profile

if [ -f $FILE ];
then
   mv /home/elight-addons/enigmalight.conf /etc/enigmalight.conf
   echo "- Config enigmalight.conf restored from backup..."
fi

if [ -f $FILE2 ];
then
   cd /
   rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight/profiles/*.profile 1>/dev/null
   cp -r /home/elight-addons/profiles /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight 1>/dev/null
   echo "- Profiles restored from backup..."
else
   cd /
   mkdir -p /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight/profiles/ 1>/dev/null
   cp -f /home/elight-addons/profiles_empty/*.profile /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight/profiles/ 1>/dev/null
   echo "- Added profiles..."
fi

echo ">>> Configuring EnigmaLight..."

sleep 1
echo
echo
echo "####################  EnigmaLight installed... ####################"
echo
echo "                  You can now use the Plugin."
echo "          Please RESTART Enigma2 to activate the GUI."
echo "         don't forget to create a configuration file :)"
echo
echo
echo ">>> For samplefiles look in /home/elight-addons/config_samples <<<"
echo
echo "###################################################################"
echo
}

pkg_postrm_${PN}() {

#!/bin/sh
echo ""
echo "Check if enigmalight is running..."
echo ""
DPID=`pidof enigmalight|head -n1`

if [ ${DPID} ]; then
	echo "enigmalight: running with PID ${DPID}, Exit..."
	kill -9 ${DPID} 2>/dev/null
else
	echo "enigmalight: not running"
fi

echo ""

rm -rf /usr/bin/enigmalight 2>/dev/null
rm -rf /usr/bin/elighttalk 2>/dev/null
rm -rf /usr/bin/elightprefix 2>/dev/null
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnigmaLight 2>/dev/null
rm -rf /home/elight-addons 2>/dev/null

echo ""
}

do_populate_sysroot[noexec] = "1"
