HOMEPAGE = "http://www.linuxtv.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "http://linuxtv.org/downloads/linuxtv-dvb-apps-${PV}.tar.bz2 \
           file://update-to-trunk.diff \
           file://update-to-tip.diff"

PR = "r2"

S = "${WORKDIR}/linuxtv-dvb-apps-${PV}"

PACKAGES =+ "dvb-evtest dvb-evtest-dbg \
             dvbapp-tests dvbapp-tests-dbg \
             dvbdate dvbdate-dbg \
             dvbtraffic dvbtraffic-dbg \
             dvbnet dvbnet-dbg \
             dvb-scan dvb-scan-dbg dvb-scan-data \
             dvb-azap dvb-azap-dbg \
             dvb-czap dvb-czap-dbg \
             dvb-szap dvb-szap-dbg \
             dvb-tzap dvb-tzap-dbg \
             dvb-femon dvb-femon-dbg \
             dvb-zap-data"


TARGET_CC_ARCH += "${LDFLAGS} -static"

FILES_${PN} = "${bindir} ${datadir}/dvb"
FILES_${PN}-doc = ""
FILES_${PN}-dev = "${includedir}"

FILES_dvb-evtest = "${bindir}/evtest"
FILES_dvb-evtest-dbg = "${bindir}/.debug/evtest"

FILES_dvbapp-tests = "${bindir}/*test* "
FILES_dvbapp-tests-dbg = "${bindir}/.debug/*test*"

FILES_dvbdate = "${bindir}/dvbdate"
FILES_dvbdate-dbg = "${bindir}/.debug/dvbdate"

FILES_dvbtraffic = "${bindir}/dvbtraffic"
FILES_dvbtraffic-dbg = "${bindir}/.debug/dvbtraffic"

FILES_dvbnet = "${bindir}/dvbnet"
FILES_dvbnet-dbg = "${bindir}/.debug/dvbnet"

FILES_dvb-scan = "${bindir}/*scan "
FILES_dvb-scan-dbg = "${bindir}/.debug/*scan"
FILES_dvb-scan-data = "${docdir}/dvb-apps/scan"

FILES_dvb-azap = "${bindir}/azap"
FILES_dvb-azap-dbg = "${bindir}/.debug/azap"

FILES_dvb-czap = "${bindir}/czap"
FILES_dvb-czap-dbg = "${bindir}/.debug/czap"

FILES_dvb-szap = "${bindir}/szap"
FILES_dvb-szap-dbg = "${bindir}/.debug/szap"

FILES_dvb-tzap = "${bindir}/tzap"
FILES_dvb-tzap-dbg = "${bindir}/.debug/tzap"

FILES_dvb-femon = "${bindir}/femon"
FILES_dvb-femon-dbg = "${bindir}/.debug/femon"

FILES_dvb-zap-data = "${docdir}/dvb-apps/szap"

do_configure() {
	sed -i -e s:/usr/include:${STAGING_INCDIR}:g util/av7110_loadkeys/generate-keynames.sh 
}

do_install() {
    make DESTDIR=${D} install
	install -d ${D}/${bindir}
    install -d ${D}/${docdir}/dvb-apps
    install -d ${D}/${docdir}/dvb-apps/scan
    install -d ${D}/${docdir}/dvb-apps/szap
	chmod a+rx ${D}/${libdir}/*.so*

    # Install tests
    install -m 0755 ${S}/test/setvoltage      ${D}${bindir}/test_setvoltage
    install -m 0755 ${S}/test/set22k          ${D}${bindir}/test_set22k
    install -m 0755 ${S}/test/video           ${D}${bindir}/test_video
    install -m 0755 ${S}/test/sendburst       ${D}${bindir}/test_sendburst
    install -m 0755 ${S}/test/diseqc          ${D}${bindir}/test_diseqc
    install -m 0755 ${S}/test/test_sections   ${D}${bindir}/
    install -m 0755 ${S}/test/test_av_play    ${D}${bindir}/
    install -m 0755 ${S}/test/test_stillimage ${D}${bindir}/
    install -m 0755 ${S}/test/test_dvr_play   ${D}${bindir}/
    install -m 0755 ${S}/test/test_tt         ${D}${bindir}/
    install -m 0755 ${S}/test/test_sec_ne     ${D}${bindir}/
    install -m 0755 ${S}/test/test_stc        ${D}${bindir}/
    install -m 0755 ${S}/test/test_av         ${D}${bindir}/
    install -m 0755 ${S}/test/test_vevent     ${D}${bindir}/
    install -m 0755 ${S}/test/test_pes        ${D}${bindir}/
    install -m 0755 ${S}/test/test_dvr        ${D}${bindir}/

    cp -pPR ${S}/util/szap/channels-conf* ${D}/${docdir}/dvb-apps/szap/
    cp -pPR ${S}/util/szap/README   ${D}/${docdir}/dvb-apps/szap/
}

python populate_packages_prepend () {
	dvb_libdir = bb.data.expand('${libdir}', d)
	do_split_packages(d, dvb_libdir, '^lib(.*)\.so$', 'lib%s', 'DVB %s package', extra_depends='', allow_links=True)
	do_split_packages(d, dvb_libdir, '^lib(.*)\.la$', 'lib%s-dev', 'DVB %s development package', extra_depends='${PN}-dev')
	do_split_packages(d, dvb_libdir, '^lib(.*)\.a$', 'lib%s-dev', 'DVB %s development package', extra_depends='${PN}-dev')
	do_split_packages(d, dvb_libdir, '^lib(.*)\.so\.*', 'lib%s', 'DVB %s library', extra_depends='', allow_links=True)
}

SRC_URI[md5sum] = "de958cdb8d00e74792dd69f3c945b037"
SRC_URI[sha256sum] = "371f8133db705bde6c2f79cccb0b1a1f626a0d94bf41acceba5d76a69155f057"
