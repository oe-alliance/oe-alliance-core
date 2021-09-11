SUMMARY = "rsync for cloud storage"
DESCRIPTION = "Rclone is a command line program to sync files and directories to and from \
 \
Amazon Drive \
Amazon S3 / Dreamhost / Ceph / Minio / Wasabi \
Backblaze B2 \
Box \
Dropbox \
FTP \
Google Cloud Storage \
Google Drive \
HTTP \
Hubic \
Jottacloud \
Mega \
Microsoft Azure Blob Storage \
Microsoft OneDrive \
OpenDrive \
Openstack Swift / Rackspace cloud files / Memset Memstore / OVH / Oracle Cloud Storage \
pCloud \
QingStor \
SFTP \
Webdav / Owncloud / Nextcloud \
Yandex Disk \
The local filesystem"
HOMEPAGE = "https://rclone.org/"

GO_IMPORT = "github.com/rclone/rclone"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/COPYING;md5=bed161b82a1ecab65ff7ba3c3b960439"

inherit go
inherit gitpkgv

SRCREV = "c2635e39cc462210500bbcbab43147096c8cdd35"
PV = "1.x+git${SRCPV}"
PKGV = "1.x+git${GITPKGV}"

S = "${WORKDIR}/git"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;destsuffix=git/src/${GO_IMPORT} \
    file://rclonefs"

INSANE_SKIP_${PN} = "ldflags file-rdeps"
INSANE_SKIP_${PN}-dev += "file-rdeps"

do_install_append() {
    rm ${D}${bindir}/test*
    install -m 755 ${WORKDIR}/rclonefs ${D}${bindir}
    ln -s rclone ${D}${bindir}/mount.rclone
}
