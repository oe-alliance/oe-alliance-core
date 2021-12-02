# Learned from CCcam
ASSUME_SHLIBS_aarch64 += "libc.so.6:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libpthread.so.0:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libstdc++.so.6:lib32-libstdc++"
ASSUME_SHLIBS_aarch64 += "libcrypt.so.1:lib32-libxcrypt"

# Learned from mgcamd
ASSUME_SHLIBS_aarch64 += "libgcc_s.so.1:lib32-libgcc"
ASSUME_SHLIBS_aarch64 += "libcrypto.so.1.0.0:lib32-libcrypto1.0.0"
ASSUME_SHLIBS_aarch64 += "libssl.so.1.0.0:lib32-libssl1.0.0"
ASSUME_SHLIBS_aarch64 += "libz.so.1:lib32-zlib"

# Learned from gbox
ASSUME_SHLIBS_aarch64 += "librt.so.1:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libm.so.6:lib32-glibc"

# Learned from wicardd
# RDEPENDS_${PN}_aarch64 = "lib32-glibc lib32-libgcc"
ASSUME_SHLIBS_aarch64 += "libdl.so.2:lib32-glibc"

# Heuristics
ASSUME_SHLIBS_aarch64 += "libcrypto.so.0.9.7:lib32-libcrypto0.9.7"
ASSUME_SHLIBS_aarch64 += "libssl.so.0.9.7:lib32-libssl0.9.7"
ASSUME_SHLIBS_aarch64 += "libcrypto.so.0.9.8:lib32-libcrypto0.9.8"
ASSUME_SHLIBS_aarch64 += "libssl.so.0.9.8:lib32-libssl0.9.8"
ASSUME_SHLIBS_aarch64 += "libcrypto.so.1.0.1:lib32-libcrypto1.0.1"
ASSUME_SHLIBS_aarch64 += "libssl.so.1.0.1:lib32-libssl1.0.1"

# Remaining libc6
ASSUME_SHLIBS_aarch64 += "libnsl.so.1:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libresolv.so.2:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libanl.so.1:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libBrokenLocale.so.1:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libutil.so.1:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libnss_dns.so.2:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libnss_compat.so.2:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "libnss_files.so.2:lib32-glibc"
ASSUME_SHLIBS_aarch64 += "ld-linux-armhf.so.3:lib32-glibc"
