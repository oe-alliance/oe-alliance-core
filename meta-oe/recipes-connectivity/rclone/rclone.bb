SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from different cloud storage providers \
    Alibaba Cloud (Aliyun) Object Storage System (OSS) Amazon Drive Amazon S3 Backblaze B2 Box Ceph DigitalOcean Spaces \
    Dreamhost Dropbox FTP Google Cloud Storage Google Drive HTTP Hubic Jottacloud IBM COS S3 Koofr Memset Memstore Mega \
    Microsoft Azure Blob Storage Microsoft OneDrive Minio Nextcloud OVH OpenDrive OpenStack Swift Oracle Cloud Storage \
    ownCloud pCloud put.io QingStor Rackspace Cloud Files Scaleway SFTP Wasabi WebDAV Yandex Disk The local filesystem"
HOMEPAGE = "https://rclone.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"

RDEPENDS:${PN} = "bash"
RDEPENDS:${PN}-dev = "bash python3-core"

inherit gittag go-mod upx-compress

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/rclone/rclone.git;protocol=https;branch=master;destsuffix=${GO_SRCURI_DESTSUFFIX} \
           file://rclonefs"

do_install() {
	install -d ${D}${bindir}
	if [ -d ${B}/bin/linux_mipsle ]; then
		install -m 755 ${B}/bin/linux_mipsle/rclone ${D}${bindir}
	elif [ -d ${B}/bin/linux_arm64 ]; then
		install -m 755 ${B}/bin/linux_arm64/rclone ${D}${bindir}
	else
		install -m 755 ${B}/bin/linux_${TARGET_ARCH}/rclone ${D}${bindir}
	fi
	install -m 755 ${UNPACKDIR}/rclonefs ${D}${bindir}
	ln -sf rclone ${D}${bindir}/mount.rclone
}
