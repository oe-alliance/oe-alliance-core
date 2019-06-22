#
# debian-package.bbclass
#

# debian-source.bbclass will generate DEBIAN_SRC_URI information
# in recipes-debian/sources/<source name>.inc

DEBIAN_SRC_URI ?= ""
SRC_URI = "${DEBIAN_SRC_URI}"

DEBIAN_UNPACK_DIR ?= "${WORKDIR}/${BP}"
S = "${DEBIAN_UNPACK_DIR}"
DPV ?= "${PV}"
DEBIAN_USE_SNAPSHOT ?= "0"
DEBIAN_SDO_URL ?= "http://snapshot.debian.org"

###############################################################################
# do_debian_unpack_extra
###############################################################################

# Make "debian" sub folder be inside source code folder
addtask debian_unpack_extra after do_unpack before do_debian_patch
do_debian_unpack_extra() {
	if [ -d ${WORKDIR}/debian ]; then
		rm -rf ${DEBIAN_UNPACK_DIR}/debian
		mv ${WORKDIR}/debian ${DEBIAN_UNPACK_DIR}/
	elif [ -f ${WORKDIR}/${BPN}_${DPV}.diff ]; then
		rm -rf ${DEBIAN_UNPACK_DIR}/debian
		cd ${DEBIAN_UNPACK_DIR}
		patch -p1 < ${WORKDIR}/${BPN}_${DPV}.diff
	fi
}

EXPORT_FUNCTIONS do_debian_unpack_extra


###############################################################################
# do_debian_patch
###############################################################################

# Check Debian source format and then decide the action.
# The meanings of return values are the follows.
#   0: native package, there is no patch
#   1: 1.0 format or custom format, need to apply patches
#   3: 3.0 quilt format, need to apply patches by quilt
debian_check_source_format() {
	FORMAT_FILE=${DEBIAN_UNPACK_DIR}/debian/source/format
	if [ ! -f ${FORMAT_FILE} ]; then
		bbnote "Debian source format is not defined, assume '1.0'"
		return 1
	fi
	FORMAT_VAL=$(cat ${FORMAT_FILE})
	bbnote "Debian source format is '${FORMAT_VAL}'"
	case "${FORMAT_VAL}" in
	"3.0 (native)")
		bbnote "nothing to do"
		return 0
		;;
	"3.0 (quilt)")
		return 3
		;;
	"3.0"*|"2.0"*)
		# FIXME: no information about how to handle
		bbfatal "unsupported source format"
		;;
	esac
	return 1
}

# Some 3.0 formatted source packages have no patch.
# Please set DEBIAN_QUILT_PATCHES = "" for such packages.
DEBIAN_QUILT_PATCHES ?= "${DEBIAN_UNPACK_DIR}/debian/patches"

DEBIAN_QUILT_DIR ?= "${DEBIAN_UNPACK_DIR}/.pc"
DEBIAN_QUILT_DIR_ESC ?= "${DEBIAN_UNPACK_DIR}/.pc.debian"

# apply patches by quilt
debian_patch_quilt() {
	# confirm that other patches didn't applied
	if [ -d ${DEBIAN_QUILT_DIR} -o -d ${DEBIAN_QUILT_DIR_ESC} ]; then
		bbfatal "unknown quilt patches already applied"
	fi

	# Some source packages don't have patch. In such cases,
	# users can intentionally ignore applying patches
	# by setting DEBIAN_QUILT_PATCHES to "".
	if [ -z "${DEBIAN_QUILT_PATCHES}" ]; then
		# Some patches are possibly added by mainteners after
		# users intentionally set DEBIAN_QUILT_PATCHES to "",
		# so here need to check if series and patch files
		# are not really included.
		if [ -s ${DEBIAN_UNPACK_DIR}/debian/patches/series ]; then
			bberror "DEBIAN_QUILT_PATCHES is null, but ${DEBIAN_UNPACK_DIR}/debian/patches/series exists"
			bbfatal "Please consider to redefine DEBIAN_QUILT_PATCHES"
		fi
		FOUND_PATCHES="$(debian_find_patches)"
		if [ -n "${FOUND_PATCHES}" ]; then
			bberror "DEBIAN_QUILT_PATCHES is null, but some patches found in ${DEBIAN_FIND_PATCHES_DIR}"
			bbfatal "Please consider to redefine DEBIAN_QUILT_PATCHES"
		fi

		# no doubt, ignore applying patches
		bbnote "no debian patch exists in the source tree, nothing to do"
		return
	fi

	# Confirmations for the following quilt command
	if [ ! -d ${DEBIAN_QUILT_PATCHES} ]; then
		bbfatal "${DEBIAN_QUILT_PATCHES} not found"
	elif [ ! -f ${DEBIAN_QUILT_PATCHES}/series ]; then
		bbfatal "${DEBIAN_QUILT_PATCHES}/series not found"
	fi
	# In some limitted packages, series is empty or comments only
	# (too strange...). Need to expressly exit here because
	# quilt command raises an error if no patch is listed in the series.
	if [ -z "$(sed '/^#/d' ${DEBIAN_QUILT_PATCHES}/series)" ]; then
		bbnote "no patch in series, nothing to do"
		return
	fi

	# apply patches
	QUILT_PATCHES=${DEBIAN_QUILT_PATCHES} quilt --quiltrc /dev/null push -a

	# avoid conflict with "do_patch"
	if [ -d ${DEBIAN_QUILT_DIR} ]; then
		mv ${DEBIAN_QUILT_DIR} ${DEBIAN_QUILT_DIR_ESC}
	fi
}

DEBIAN_DPATCH_PATCHES ?= "${DEBIAN_UNPACK_DIR}/debian/patches"
# apply patches by dpatch
debian_patch_dpatch() {
	# Replace hardcode path in patch files
	find ${DEBIAN_DPATCH_PATCHES} -name "*.dpatch" -type f -exec sed -i \
	    -e "s@^#! /bin/sh /usr/share/dpatch/dpatch-run@#! /usr/bin/env dpatch-run@g" {} \;

	export PATH="${STAGING_DATADIR_NATIVE}/dpatch:$PATH"
	dpatch apply-all
}

DEBIAN_FIND_PATCHES_DIR ?= "${DEBIAN_UNPACK_DIR}/debian"

debian_find_patches() {
	find ${DEBIAN_FIND_PATCHES_DIR} \
		-name "*.patch" -o \
		-name "*.diff" \
		-type f
}

# used only when DEBIAN_PATCH_TYPE is "abnormal"
# this is very rare case; should not be used except
# the cases that all other types cannot be used
# this function must be overwritten by each recipe
debian_patch_abnormal() {
	bbfatal "debian_patch_abnormal not defined"
}

# decide an action to apply patches for the source package
# candidates: quilt, dpatch, nopatch, abnormal
DEBIAN_PATCH_TYPE ?= ""

addtask debian_patch after do_unpack before do_patch
do_debian_patch[dirs] = "${DEBIAN_UNPACK_DIR}"
do_debian_patch[depends] += "${@oe.utils.conditional(\
    'PN', 'quilt-native', '', 'quilt-native:do_populate_sysroot', d)}"
do_debian_patch[depends] += "${@oe.utils.conditional(\
    'DEBIAN_PATCH_TYPE', 'dpatch', 'dpatch-native:do_populate_sysroot', '', d)}"
do_debian_patch() {
	if debian_check_source_format; then
		return 0
	else
		FORMAT=$?
	fi
	# apply patches according to the source format
	case ${FORMAT} in
	1)
		# DEBIAN_PATCH_TYPE must be set manually to decide
		# an action when Debian source format is not 3.0
		if [ -z "${DEBIAN_PATCH_TYPE}" ]; then
			bbfatal "DEBIAN_PATCH_TYPE not set"
		fi

		bbnote "DEBIAN_PATCH_TYPE: ${DEBIAN_PATCH_TYPE}"
		if [ "${DEBIAN_PATCH_TYPE}" = "quilt" ]; then
			debian_patch_quilt
		elif [ "${DEBIAN_PATCH_TYPE}" = "dpatch" ]; then
			debian_patch_dpatch
		elif [ "${DEBIAN_PATCH_TYPE}" = "nopatch" ]; then
			# No patch and no function to apply patches in
			# some source packages. In such cases, confirm
			# that really no patch-related file is included
			FOUND_PATCHES=$(debian_find_patches)
			if [ -n "${FOUND_PATCHES}" ]; then
				bberror "the following patches found:"
				for p in ${FOUND_PATCHES}; do
					bberror ${p}
				done
				bbfatal "please re-consider DEBIAN_PATCH_TYPE"
			fi
		elif [ "${DEBIAN_PATCH_TYPE}" = "abnormal" ]; then
			debian_patch_abnormal
		else
			bbfatal "invalid DEBIAN_PATCH_TYPE: ${DEBIAN_PATCH_TYPE}"
		fi
		;;
	3)
		debian_patch_quilt
		;;
	esac
}
EXPORT_FUNCTIONS do_debian_patch


python () {
    import json, os, re

    # get json data from snapshot.d.o
    def _get_sdo_json_data(path):
        import urllib.request

        base_url = d.getVar("DEBIAN_SDO_URL", True)
        try:
            readobj = urllib.request.urlopen(base_url + path)
        except urllib.error.URLError as e:
            bb.fatal('Can not access to %s' % base_url + path)
            print(e.reason)
        else:
            return readobj.read()

    # get json data from snapshot.d.o or files
    def _get_sdo_json (api_path, json_path):
        if os.path.exists(json_path):
            with open(json_path) as f:
                return f.read()
        else:
            _data = _get_sdo_json_data(api_path)
            if _data is not None:
                with open(json_path, mode = 'w') as f:
                    data = _data.decode('utf-8')
                    f.write(data)
                    return data

    # get json data of source files
    def _get_sdo_json_srcfiles (dl_dir, pkgname, pkgver):
        api_path = os.path.join('/mr/package/', pkgname, pkgver, 'srcfiles')
        json_path = os.path.join(dl_dir, pkgname + '_' + pkgver + '_srcfiles.json')

        return _get_sdo_json(api_path, json_path)

    # get json data of pkg info
    def _get_sdo_json_pkginfo (dl_dir, filehash, pkgname, pkgver):
        api_path = os.path.join('/mr/file/', filehash, 'info')
        json_path = os.path.join(dl_dir, pkgname + '_' + pkgver + '_' + filehash +'_info.json')

        return _get_sdo_json(api_path, json_path)

    def _get_sdo_spkgdata (pkgname, filename, fileinfo):
        jsondata = json.loads(fileinfo)

        # result data check
        results = jsondata['result']
        if results is None:
            return

        for i in range(len(results)):
            name = results[i]['name']
            archive_name = results[i]['archive_name']

            # compare file names
            if name == filename:
                p = name.split('_')
                if p[0] in pkgname:
                    archive_path = results[i]['path']
                    first_seen = results[i]['first_seen']

                    return name, archive_path, first_seen, archive_name

    if d.getVar("DEBIAN_USE_SNAPSHOT", True) == "0":
        return

    # check DEBIAN_SRC_URI
    debian_src_uri_orig = d.getVar('DEBIAN_SRC_URI', True).split()
    if len(debian_src_uri_orig) == 0:
        bb.bbfatal('There is no data for DEBIAN_SRC_URI.')
        return

    pkgname = ""
    # Get source package name from original source uri (DEBIAN_SRC_URI)
    for _pkg_uri in debian_src_uri_orig:
        if ".dsc" in _pkg_uri:
            _pkg_file_name = os.path.basename(_pkg_uri)
            pkgname = _pkg_file_name.split(";")[0].split("_")[0]
            break

    if len(pkgname) == 0:
        bb.bbfatal('There is no Debian source package name.')
        return

    pkgver = d.getVar("DPV", True)
    if pkgver is None:
        pkgver = d.getVar("PV", True)
    # get epoch
    pkgepoch = d.getVar("DPV_EPOCH", True)
    if pkgepoch != '':
        pkgver = pkgepoch + ':' + pkgver

    dl_dir = d.getVar('DL_DIR', True)

    # check and create DL_DIR
    if os.path.exists(dl_dir) is not True:
        os.makedirs(dl_dir)

    # get src files from json
    srcfiles = _get_sdo_json_srcfiles(dl_dir, pkgname, pkgver)
    if srcfiles is None:
        return

    jsondata = json.loads(srcfiles)

    # check src package name
    if jsondata['package'] not in pkgname:
        return

    # check src package version
    if jsondata['version'] not in pkgver:
        return

    # check result data
    results = jsondata['result']
    if results is None:
        return

    debfile_urls = ''

    for i in range(len(results)):
        prevent_apply = ""
        filehash = results[i]['hash']
        pkginfo = _get_sdo_json_pkginfo(dl_dir, filehash, pkgname, pkgver)
        if pkginfo is None:
            continue

        for _uri in debian_src_uri_orig:
            # get filename from DEBIAN_SRC_URI
            _match_data = re.search(r"^.*/(\S.*);name=\S.*$", _uri)
            if _match_data:
                # get package infomation form json
                info = _get_sdo_spkgdata(pkgname, _match_data.group(1), pkginfo)
                if info:
                    break
            else:
                continue

        if info is None:
            continue

        base_url = d.getVar("DEBIAN_SDO_URL", True)
        u = "%s/archive/%s/%s/%s" % (base_url, info[3], info[2], info[1])
        if ".diff" in info[0] or ".patch" in info[0]:
            prevent_apply = ";apply=no"
        nametag = info[0].replace('~', '_')
        debfile_urls += '%s/%s;name=%s%s ' % (u, info[0], nametag, prevent_apply)

    if not debfile_urls:
        bb.bbfatal('Can not get URI of debian source packages.')
        return None

    # overwrite DEBIAN_SRC_URI
    d.setVar('DEBIAN_SRC_URI', debfile_urls)
}
