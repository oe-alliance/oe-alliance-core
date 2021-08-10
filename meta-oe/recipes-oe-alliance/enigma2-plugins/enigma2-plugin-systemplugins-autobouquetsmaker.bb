SUMMARY = "Automatically build and update bouquets from the DVB stream."
DESCRIPTION = "Automatically build and update bouquets from the DVB stream."
MAINTAINER = "oe-alliance team"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

inherit autotools-brokensep gitpkgv pythonnative gettext

SRCREV = "${AUTOREV}"
PV = "3.3+git${SRCPV}"
PKGV = "3.3+git${GITPKGV}"
PR = "r0"

SRC_URI = "git://github.com/oe-alliance/AutoBouquetsMaker.git;protocol=git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    "

S = "${WORKDIR}/git"

DEPENDS = "enigma2"

INSANE_SKIP_${PN} += "already-stripped ldflags"

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
echo "Checking for an ABM cache file"

if [ -f /usr/lib/enigma2/python/Plugins/SystemPlugins/AutoBouquetsMaker/providers/providers.cache ]; then
	rm -f /usr/lib/enigma2/python/Plugins/SystemPlugins/AutoBouquetsMaker/providers/providers.cache > /dev/null 2>&1
	echo "Cache file has been removed"
else
	echo "No cache file found, continuing."
fi
}
