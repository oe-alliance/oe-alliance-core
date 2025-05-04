DESCRIPTION = "tool to look up your Wan IP at whatsmyip.com"
MAINTAINER = "Huevos"

inherit gitpkgv allarch python3native

require conf/license/license-gplv2.inc

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/Huevos/WanIP.git;protocol=https;branch=master"

RDEPENDS:${PN} = "\
    python3-requests \
    "

S = "${WORKDIR}/git"

pluginpath = "/usr/lib/enigma2/python/Plugins/SystemPlugins/WanIP"

do_install:append() {
	install -d ${D}${pluginpath}
	cp -r ${S}/src/* ${D}${pluginpath}/
	python3 -m compileall -o2 -b ${D}
	if [ -f /usr/bin/msgfmt ] ; then
		find ${S}/po/ -maxdepth 1 -type f -name '*.po' | while read po ; do
			## remove everything before and including the "/"
			filename=${po##*/}
			## remove everything after and including the "."
			cc=${filename%%.*}
			folder=${D}${pluginpath}/locale/${cc}/LC_MESSAGES
			mkdir -p ${folder}
			/usr/bin/msgfmt -o ${folder}/iptv-org-playlists.mo ${po}
		done
	fi
}

FILES:${PN} = "${pluginpath}/"

FILES:${PN}-src = "${pluginpath}/*.py"
