SUMMARY = "Additional plugins for Enigma2"
MAINTAINER = "OE-Alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PACKAGES_DYNAMIC = "enigma2-plugin-(?!oea-).*"
PROVIDES = "${PN} \
    enigma2-plugin-extensions-fancontrol2 \
    "

inherit autotools-brokensep gitpkgv ${PYTHON_PN}native pkgconfig gettext ${PYTHON_PN}-dir

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+gitr${SRCPV}"
PKGV = "${IMAGE_VERSION}+gitr${GITPKGV}"
PR = "r8"

SRC_URI = "${ENIGMA2_PLUGINS_URI} file://pluginnotwanted.patch \
        file://ax-python-devel-dont-check-for-distutils.patch"
SRC_URI:append:openatv = " file://EPGSearch.patch"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --without-debug \
    --with-po \
    --with-boxtype=${MACHINE} \
    --with-distro=${DISTRO} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'tpm', '--with-tpm' , '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'pli', '--with-pli' , '', d)} \
"

CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"
CXXFLAGS = " -std=c++11"

RREPLACES:enigma2-plugin-skincomponents-eventlist = "enigma2-plugin-components-eventlist"
RCONFLICTS:enigma2-plugin-skincomponents-eventlist = "enigma2-plugin-components-eventlist"

CONFFILES:enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"
CONFFILES:enigma2-plugin-extensions-babelzapper += " ${sysconfdir}/babelzapper"
CONFFILES:enigma2-plugin-extensions-netcaster += " ${sysconfdir}/NETcaster.conf"
CONFFILES:enigma2-plugin-extensions-podcast += " ${sysconfdir}/podcast/podcasts.xml"
CONFFILES:enigma2-plugin-extensions-seriesplugin += " ${sysconfdir}/enigma2/seriesplugin_pattern_directories.json ${sysconfdir}/enigma2/seriesplugin_patterns.json"
FILES:${PN} += " /usr/share/enigma2 /usr/share/fonts "
FILES:${PN}-meta = "${datadir}/meta"
FILES:enigma2-plugin-extensions-bmediacenter += " ${libdir}/enigma2/python/Components/Renderer/LizWatches.pyc ${libdir}/enigma2/python/Components/Converter/LizExtraNumText.pyc"
FILES:enigma2-plugin-skincomponents-channelselectionshorttitle += " ${libdir}/enigma2/python/Components/Converter/ChannelSelectionShortTitle.pyc"
FILES:enigma2-plugin-skincomponents-eventlist += " ${libdir}/enigma2/python/Components/Renderer/EventListDisplay.pyc ${libdir}/enigma2/python/Components/Converter/EventList.pyc"
FILES:enigma2-plugin-skincomponents-eventposition += " ${libdir}/enigma2/python/Components/Converter/EventPosition.pyc"
FILES:enigma2-plugin-skincomponents-weathercomponent += " ${libdir}/enigma2/python/Components/WeatherMSN.pyc ${libdir}/enigma2/python/Components/Converter/MSNWeather.pyc ${libdir}/enigma2/python/Components/Sources/MSNWeather.pyc ${libdir}/enigma2/python/Components/Renderer/MSNWeatherPixmap.pyc"
FILES:enigma2-plugin-skincomponents-reftopiconname += " ${libdir}/enigma2/python/Components/Converter/RefToPiconName.pyc"
FILES:enigma2-plugin-extensions-babelzapper += " ${sysconfdir}/babelzapper"
FILES:enigma2-plugin-extensions-netcaster += " ${sysconfdir}/NETcaster.conf"
FILES:enigma2-plugin-extensions-podcast += " ${sysconfdir}/podcast/podcasts.xml"
FILES:enigma2-plugin-extensions-seriesplugin += " ${sysconfdir}/enigma2/seriesplugin_pattern_directories.json ${sysconfdir}/enigma2/seriesplugin_patterns.json"
FILES:enigma2-plugin-extensions-movietagger += "${sysconfdir}/enigma2/movietags"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/Renderer/LizWatches.py /usr/lib/enigma2/python/Components/Converter/LizExtraNumText.py"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/Converter/ChannelSelectionShortTitle.py"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/Renderer/EventListDisplay.py /usr/lib/enigma2/python/Components/Converter/EventList.py"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/Converter/EventPosition.py"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/WeatherMSN.py /usr/lib/enigma2/python/Components/Converter/MSNWeather.py /usr/lib/enigma2/python/Components/Sources/MSNWeather.py /usr/lib/enigma2/python/Components/Renderer/MSNWeatherPixmap.py"
FILES:${PN}-src += " /usr/lib/enigma2/python/Components/Converter/RefToPiconName.py"



PACKAGES += "${PN}-meta ${PN}-build-dependencies"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

DEPENDS = "enigma2 \
    ${PYTHON_PN}-pyopenssl \
    ${PYTHON_PN}-gdata-python3 \
    streamripper \
    ${PYTHON_PN}-mutagen \
    ${PYTHON_PN}-twisted \
    ${PYTHON_PN}-daap \
    ${PYTHON_PN}-google-api-python-client \
    ${PYTHON_PN}-httplib2 \
    ${PYTHON_PN}-youtube-dl \
    ${PYTHON_PN}-yt-dlp \
    ${PYTHON_PN}-six-native \
    dvdbackup \
    libav \
    libshowiframe \
    libcddb \
    libtirpc \
    nmap \
    "

RDEPENDS:${PN} = "${PYTHON_PN}-ctypes"

python populate_packages:prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)

    def getControlLines(mydir, d, package):
        packagename = package[-1]
        import os
        try:
            #ac3lipsync is renamed since 20091121 to audiosync.. but rename in cvs is not possible without lost of revision history..
            #so the foldername is still ac3lipsync
            if packagename == 'audiosync':
                packagename = 'ac3lipsync'
            src = open(mydir + packagename + "/CONTROL/control").read()
        except IOError:
            return
        for line in src.split("\n"):
            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            if line.startswith('Depends: '):
                # some plugins still reference twisted-* dependencies, these packages are now called ${PYTHON_PN}-twisted-*
                rdepends = []
                for depend in line[9:].split(','):
                    depend = depend.strip()
                    if depend.startswith('twisted-'):
                        rdepends.append(depend.replace('twisted-', '${PYTHON_PN}-twisted-'))
                    elif depend == 'python-re' or depend == 'python-lang' or depend == 'python-textutils':
                        pass
                    elif depend.startswith('python-'):
                        rdepends.append(depend.replace('python-', '${PYTHON_PN}-'))
                    elif depend.startswith('gst-plugins-'):
                        rdepends.append(depend.replace('gst-plugins-', 'gstreamer1.0-'))
                    elif depend.startswith('enigma2') and not depend.startswith('enigma2-'):
                        pass # Ignore silly depends on enigma2 with all kinds of misspellings
                    else:
                        rdepends.append(depend)
                rdepends = ' '.join(rdepends)
                d.setVar('RDEPENDS:' + full_package, rdepends)
            elif line.startswith('Recommends: '):
                d.setVar('RRECOMMENDS:' + full_package, line[12:])
            elif line.startswith('Description: '):
                d.setVar('DESCRIPTION:' + full_package, line[13:])
            elif line.startswith('Replaces: '):
                d.setVar('RREPLACES:' + full_package, ' '.join(line[10:].split(', ')))
            elif line.startswith('Conflicts: '):
                d.setVar('RCONFLICTS:' + full_package, ' '.join(line[11:].split(', ')))
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER_' + full_package, line[12:])

    mydir = d.getVar('D', True) + "/../git/"
    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

pkg_preinst:enigma2-plugin-extensions-webinterface() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/WebInterface
exit 0
}

do_package_qa() {
}
