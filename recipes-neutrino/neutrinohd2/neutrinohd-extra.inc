require neutrinohd-bin.inc

PR_append = ".33"

dirs755 = "\
	/usr/bin \
	/usr/sbin \
	/usr/lib \
	/usr/share/fonts \
	/usr/share/iso-codes \
	/usr/share/tuxbox/lcdd/icons \
	/usr/share/tuxbox/neutrino/httpd \
	/usr/share/tuxbox/neutrino/httpd/images \
	/usr/share/tuxbox/neutrino/httpd/languages \
	/usr/share/tuxbox/neutrino/httpd/scripts \
	/usr/share/tuxbox/neutrino/icons \
	/usr/share/tuxbox/neutrino/locale \
	/usr/share/tuxbox/neutrino/themes \
	/etc/tuxbox/config \
	/etc/tuxbox/config/tuxtxt \
	/etc/tuxbox/config/zapit \
	/etc/tuxbox/plugins \
	"

FILES_${PN} = "${dirs755}"

inherit autotools

do_install_append() {
}

pkg_preinst_${PN} () {
}

pkg_postinst_${PN} () {
#!/bin/sh
cd $D/usr/lib
if [ ! -e libavcodec.so.52  ]; then if [ -e libavcodec.so.53  ]; then ln -s libavcodec.so.53  libavcodec.so.52;  fi; fi
if [ ! -e libavformat.so.52 ]; then if [ -e libavformat.so.53 ]; then ln -s libavformat.so.53 libavformat.so.52; fi; fi
if [ ! -e libavutil.so.49   ]; then if [ -e libavutil.so.51   ]; then ln -s libavutil.so.51   libavutil.so.49;   fi; fi
cd - > /dev/null
echo " "
echo " "
echo " "
echo " "
echo " "
echo "neutrinoHD2 successfully installed!"
echo " "
echo "have fun with neutrino!!!"
#sleep 15
echo " "
echo " please restart your STB!!!"
}

pkg_postrm_${PN} () {
#!/bin/sh

rm -rf /usr/bin/neutrino
rm -rf /usr/bin/pzapit
rm -rf /usr/bin/sectionsdcontrol
rm -rf /usr/bin/backup.sh
rm -rf /usr/bin/restore.sh
rm -rf /usr/bin/install.sh
rm -rf /usr/bin/init_hdd.sh

rm -rf /usr/sbin/udpstreampes

rm -rf /usr/share/fonts/neutrino.ttf

rm -rf /usr/share/iso-codes/iso-639.tab
rm -R /usr/share/tuxbox/neutrino/

rm -rf /etc/tuxbox/config/Y-Web.conf
rm -rf /etc/tuxbox/config/cables.xml
rm -rf /etc/tuxbox/config/moviebrowser.conf
rm -rf /etc/tuxbox/config/nhttpd.conf
rm -rf /etc/tuxbox/config/radio-stations.xml
rm -rf /etc/tuxbox/config/satellites.xml
rm -rf /etc/tuxbox/config/terrestrial.xml
rm -rf /etc/tuxbox/config/timerd.conf
rm -rf /etc/tuxbox/config/giga.conf

rm -R /etc/tuxbox/config/tuxtxt/
rm -R /etc/tuxbox/config/zapit/
rm -R /etc/tuxbox/plugins

echo " "
echo " "
echo " "
echo " "
echo " "
echo "neutrino successfully removed! please restart enigma2!"
sleep 15
exit 0
}

