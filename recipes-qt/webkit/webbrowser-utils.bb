DESCRIPTION = "arora webbrowser binary"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PV = "0.10.1"
PR = "r5"

require conf/license/license-gplv2.inc

DEPENDS = "qtwebkit-e"

SRC_URI = "http://archiv.openmips.com/arora_vuplus.tar.gz;name=vuplus"
SRC_URI_et5x00 = "http://archiv.openmips.com/arora_et5x00.tar.gz;name=et5x00"
SRC_URI_et6x00 = "http://archiv.openmips.com/arora_et6x00.tar.gz;name=et6x00"
SRC_URI_et9x00 = "http://archiv.openmips.com/arora_et9x00.tar.gz;name=et9x00"
SRC_URI_dm7020hd = "http://archiv.openmips.com/arora_dm7020hd.tar.gz;name=dm7020hd"
SRC_URI_dm8000 = "http://archiv.openmips.com/arora_dm8000.tar.gz;name=dm8000"
SRC_URI_odinm9 = "http://archiv.openmips.com/arora_odinm9.tar.gz;name=odinm9"
SRC_URI_gbquad = "http://archiv.openmips.com/arora_gbquad.tar.gz;name=gbquad"


SRC_URI[vuplus.md5sum] = "2d4626aedf7ec0441d533f1ac95e7342"
SRC_URI[vuplus.sha256sum] = "f29e18ba5f7d56f05eb154e78ef4c16048a0b4ba6978b8f9f6ef6c1ab2cdf0a4"
SRC_URI[et5x00.md5sum] = "3c0726f897362cae6d73b0a0e78d92b1"
SRC_URI[et5x00.sha256sum] = "212af0b1b380eb0cf3337c2498e0941967a3be6198716ae4567e49ec4c914e48"
SRC_URI[et6x00.md5sum] = "c2a826c8e5031e42b9f4282e4b7f9ad3"
SRC_URI[et6x00.sha256sum] = "d9b31e486adf288765436218e459843f299daa6137c8f120bdc4a91bf8b736b3"
SRC_URI[et9x00.md5sum] = "4cb73229454daf9c7f3996bc033fd197"
SRC_URI[et9x00.sha256sum] = "4e8614018065a1cc4f7b8530d59f69bb8d51cb510461fcf8f0dd9314155e1aa7"
SRC_URI[dm7020hd.md5sum] = "3719df7b0e5188a8a6a33f76e2373604"
SRC_URI[dm7020hd.sha256sum] = "3e812b1351eb4e13fb1fd3d9be17ee85c3dcdba0df9834ad0474754b73e9480a"
SRC_URI[dm8000.md5sum] = "68c652861e87e4343f9d2e3c429e4bd7"
SRC_URI[dm8000.sha256sum] = "4d6a4c50843f713ebc24dab0aa427566a8b3d4ee6d5bb27cc6880d6f6b3ec895"
SRC_URI[odinm9.md5sum] = "089095aa3bcf5bcbe42dcd116afbede9"
SRC_URI[odinm9.sha256sum] = "524ac7dcd2a94cd7b8ad8b5fc043a6351fbbc80affc171360fcb493c214971ab"
SRC_URI[gbquad.md5sum] = "2894d903f18199d706f897e39d8f77a8"
SRC_URI[gbquad.sha256sum] = "4f14411a44feb5befece23dfd06705142b9a96f1a9f11a7aaa976b915cb1caa6"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/${bindir};
	install -m 0755 arora ${D}/${bindir};
}
