#
# debian-verify-version.bbclass
#
# Compare recipe version, which defaults to PV,
# with Debian source version which is parsed from debian/changelog
#

RECIPE_VERSION ?= "${PV}"
DEBIAN_CHANGELOG ?= "${DEBIAN_UNPACK_DIR}/debian/changelog"

addtask debian_verify_version after do_unpack before do_debian_fix_timestamp
do_debian_verify_version[dirs] = "${DEBIAN_UNPACK_DIR}"
do_debian_verify_version() {
	if [ ! -f ${DEBIAN_CHANGELOG} ]; then
		bbfatal "Could not find ${DEBIAN_CHANGELOG}."
	fi

	# Based on /usr/share/cdbs/1/rules/buildvars.mk.
	# Get source package version from debian/changelog.
	# About non Debian native packages, which version includes '-',
	# DEB_SRC_VERSION means the upstream version.
	# About Debian native packages, DEB_SRC_VERSION is
	# just same as DEB_NOEPOCH_VERSION.
	DEB_VERSION=$(head -n 1 ${DEBIAN_CHANGELOG} | sed "s@[^()]*(\([^()]*\)).*@\1@")
	DEB_NOEPOCH_VERSION=$(echo $DEB_VERSION | cut -d: -f2-)
	DEB_SRC_VERSION=$(echo $DEB_NOEPOCH_VERSION | sed 's/-[^-]*$//')

	if [ x"$DEB_SRC_VERSION" = x ]; then
		bbfatal "Could not parse source code version."
	elif [ "${DEB_SRC_VERSION}" != "${RECIPE_VERSION}" ]; then
		bbwarn "${PN}: Source code version (${DEB_SRC_VERSION}) and recipe version (${RECIPE_VERSION}) mismatch"
	fi
}

EXPORT_FUNCTIONS do_debian_verify_version
