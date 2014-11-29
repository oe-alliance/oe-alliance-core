SUMMARY = "Additional plugins for Enigma2"
MAINTAINER = "OE-Alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

PACKAGES_DYNAMIC = "enigma2-plugin-(?!oea-).*"
PROVIDES = "${PN} \
    enigma2-plugin-extensions-fancontrol2 \
    "

inherit autotools-brokensep gitpkgv pythonnative pkgconfig

PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r8"

SRC_URI = "${ENIGMA2_PLUGINS_URI} file://pluginnotwanted.patch"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --without-debug \
    --with-po \
    --with-boxtype=${MACHINE} \
    --with-distro=${DISTRO} \
    --with-gstversion=1.0 \
    ${@base_contains('MACHINE_FEATURES', 'tpm', '--with-tpm' , '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'pli', '--with-pli' , '', d)} \
"

RREPLACES_enigma2-plugin-skincomponents-eventlist = "enigma2-plugin-components-eventlist"
RCONFLICTS_enigma2-plugin-skincomponents-eventlist = "enigma2-plugin-components-eventlist"

CONFFILES_${PN} += "${sysconfdir}/enigma2/movietags"
FILES_${PN} += " /usr/share/enigma2 /usr/share/fonts "
FILES_${PN}-meta = "${datadir}/meta"
FILES_enigma2-plugin-extensions-bmediacenter += " ${libdir}/enigma2/python/Components/Renderer/LizWatches.pyo ${libdir}/enigma2/python/Components/Converter/LizExtraNumText.pyo"
FILES_enigma2-plugin-extensions-bmediacenter-src += " ${libdir}/enigma2/python/Components/Renderer/LizWatches.py ${libdir}/enigma2/python/Components/Converter/LizExtraNumText.py"
FILES_enigma2-plugin-skincomponents-channelselectionshorttitle += " ${libdir}/enigma2/python/Components/Converter/ChannelSelectionShortTitle.pyo"
FILES_enigma2-plugin-skincomponents-channelselectionshorttitle-src += " ${libdir}/enigma2/python/Components/Converter/ChannelSelectionShortTitle.py"
FILES_enigma2-plugin-skincomponents-eventlist += " ${libdir}/enigma2/python/Components/Renderer/EventListDisplay.pyo ${libdir}/enigma2/python/Components/Converter/EventList.pyo"
FILES_enigma2-plugin-skincomponents-eventlist-src += " ${libdir}/enigma2/python/Components/Renderer/EventListDisplay.py ${libdir}/enigma2/python/Components/Converter/EventList.py"
FILES_enigma2-plugin-skincomponents-eventposition += " ${libdir}/enigma2/python/Components/Converter/EventPosition.pyo"
FILES_enigma2-plugin-skincomponents-eventposition-src += " ${libdir}/enigma2/python/Components/Converter/EventPosition.py"
FILES_enigma2-plugin-skincomponents-weathercomponent += " ${libdir}/enigma2/python/Components/WeatherMSN.pyo ${libdir}/enigma2/python/Components/Converter/MSNWeather.pyo ${libdir}/enigma2/python/Components/Sources/MSNWeather.pyo ${libdir}/enigma2/python/Components/Renderer/MSNWeatherPixmap.pyo"
FILES_enigma2-plugin-skincomponents-weathercomponent-src += " ${libdir}/enigma2/python/Components/WeatherMSN.py ${libdir}/enigma2/python/Components/Converter/MSNWeather.py ${libdir}/enigma2/python/Components/Sources/MSNWeather.py ${libdir}/enigma2/python/Components/Renderer/MSNWeatherPixmap.py"
FILES_enigma2-plugin-skincomponents-reftopiconname += " ${libdir}/enigma2/python/Components/Converter/RefToPiconName.pyo"
FILES_enigma2-plugin-skincomponents-reftopiconname-src += " ${libdir}/enigma2/python/Components/Converter/RefToPiconName.py"

PACKAGES += "${PN}-meta enigma2-plugin-skincomponents-channelselectionshorttitle-src enigma2-plugin-skincomponents-eventlist-src enigma2-plugin-skincomponents-eventposition-src enigma2-plugin-skincomponents-weathercomponent-src enigma2-plugin-skincomponents-reftopiconname enigma2-plugin-skincomponents-reftopiconname-src"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

DEPENDS = "enigma2 \
    python-pyopenssl \
    python-gdata \
    streamripper \
    python-mutagen \
    python-twisted \
    python-daap \
    dvdbackup \
    libcddb \
    nmap \
    libshowiframe \
    libav \
    "

python populate_packages_prepend() {
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
                # some plugins still reference twisted-* dependencies, these packages are now called python-twisted-*
                rdepends = []
                for depend in line[9:].split(','):
                    depend = depend.strip()
                    if depend.startswith('twisted-'):
                        rdepends.append(depend.replace('twisted-', 'python-twisted-'))
                    elif depend.startswith('enigma2') and not depend.startswith('enigma2-'):
                        pass # Ignore silly depends on enigma2 with all kinds of misspellings
                    else:
                        rdepends.append(depend)
                rdepends = ' '.join(rdepends)
                bb.data.setVar('RDEPENDS_' + full_package, rdepends, d)
            elif line.startswith('Recommends: '):
                bb.data.setVar('RRECOMMENDS_' + full_package, line[12:], d)
            elif line.startswith('Description: '):
                bb.data.setVar('DESCRIPTION_' + full_package, line[13:], d)
            elif line.startswith('Replaces: '):
                bb.data.setVar('RREPLACES_' + full_package, ' '.join(line[10:].split(', ')), d)
            elif line.startswith('Conflicts: '):
                bb.data.setVar('RCONFLICTS_' + full_package, ' '.join(line[11:].split(', ')), d)
            elif line.startswith('Maintainer: '):
                bb.data.setVar('MAINTAINER_' + full_package, line[12:], d)

    mydir = bb.data.getVar('D', d, 1) + "/../git/"
    for package in bb.data.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

pkg_preinst_enigma2-plugin-extensions-webinterface() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/WebInterface
exit 0
}
