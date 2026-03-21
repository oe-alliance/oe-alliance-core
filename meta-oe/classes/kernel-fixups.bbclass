# kernel-fixups.bbclass
# Auto-fix common kernel build issues that affect multiple kernel versions.
# Replaces version-specific patch files with auto-detecting sed fixes.

# Shared patches across kernel versions may apply with offset — suppress fuzz warnings
WARN_QA:remove = "patch-fuzz"

do_patch[postfuncs] += "kernel_fixups_apply"

kernel_fixups_apply() {
    # Fix multiple definitions of yylloc (affects kernels with dtc-parser.tab.c_shipped)
    if [ -f "${S}/scripts/dtc/dtc-parser.tab.c_shipped" ]; then
        if grep -q '^YYLTYPE yylloc' "${S}/scripts/dtc/dtc-parser.tab.c_shipped"; then
            sed -i 's/^YYLTYPE yylloc/extern YYLTYPE yylloc/' "${S}/scripts/dtc/dtc-parser.tab.c_shipped"
            bbnote "kernel-fixups: applied yyloc fix"
        fi
    fi

    # Fix ARM assembler Solaris-style section flags (ARM: 8933/1)
    # Replaces Sun/Solaris style #alloc/#execinstr with GNU style "ax"/"a"
    for f in \
        "${S}/arch/arm/boot/bootp/init.S" \
        "${S}/arch/arm/boot/compressed/big-endian.S" \
        "${S}/arch/arm/boot/compressed/head.S" \
        "${S}/arch/arm/boot/compressed/piggy.lzma.S" \
        "${S}/arch/arm/mm/proc-v7.S"; do
        if [ -f "$f" ] && grep -q '#alloc' "$f"; then
            sed -i \
                -e 's/,#alloc,#execinstr/, "ax"/' \
                -e 's/, #alloc, #execinstr/, "ax"/' \
                -e 's/,#alloc/, "a"/' \
                -e 's/, #alloc/, "ax"/' \
                "$f"
            bbnote "kernel-fixups: applied ARM-8933 section flag fix to $f"
        fi
    done

    # Fix GCC const-register warning in uaccess.h (affects ARM kernels 3.x/4.x)
    # Removes 'const' qualifier from register variable __r2 to avoid GCC warnings
    UACCESS="${S}/arch/arm/include/asm/uaccess.h"
    if [ -f "$UACCESS" ] && grep -q 'register const typeof.*(.*) __r2 asm' "$UACCESS"; then
        sed -i 's/register const typeof(\*(p)) __r2 asm/register typeof(*(p)) __r2 asm/' "$UACCESS"
        bbnote "kernel-fixups: applied const-register fix to uaccess.h"
    fi

    # SMB dialect upgrade: SMB1 -> SMB3 (or SMB2 for older kernels without SMB3 support)
    # Skip when the cifs Kbuild guards SMB2 behind CONFIG_CIFS_SMB2 — those kernels
    # (e.g. 3.6) may declare smb21_operations but only compile them when enabled.
    # Newer kernels (4.x+) removed the guard and always build SMB2/3.
    # Note: .config is not available during do_patch, so we check the source Makefile.
    CIFS_MK="${S}/fs/cifs/Makefile"
    if [ -f "${S}/fs/cifs/connect.c" ] && grep -q 'vol->ops = &smb1_operations' "${S}/fs/cifs/connect.c" && \
       ! grep -q 'CONFIG_CIFS_SMB2' "$CIFS_MK" 2>/dev/null; then
        if grep -q 'smb30_operations' "${S}/fs/cifs/cifsglob.h" 2>/dev/null; then
            sed -i \
                -e 's/vol->ops = &smb1_operations;/vol->ops = \&smb30_operations; \/* both secure and accepted widely *\//' \
                -e 's/vol->vals = &smb1_values;/vol->vals = \&smb302_values;/' \
                -e 's/FIXME: add autonegotiation -- for now, SMB1 is default/FIXME: add autonegotiation for SMB3 or later rather than just SMB3/' \
                "${S}/fs/cifs/connect.c"
            bbnote "kernel-fixups: applied SMB3 dialect upgrade"
        elif grep -q 'smb21_operations' "${S}/fs/cifs/cifsglob.h" 2>/dev/null; then
            sed -i \
                -e 's/vol->ops = &smb1_operations;/vol->ops = \&smb21_operations; \/* both secure and accepted widely *\//' \
                -e 's/vol->vals = &smb1_values;/vol->vals = \&smb21_values;/' \
                -e 's/FIXME: add autonegotiation -- for now, SMB1 is default/FIXME: add autonegotiation for SMB2 or later rather than just SMB2/' \
                "${S}/fs/cifs/connect.c"
            bbnote "kernel-fixups: applied SMB2 dialect upgrade"
        fi
    fi

    # NFS max rw size: 1MB -> 8KB (for set-top box memory constraints)
    if [ -f "${S}/include/linux/nfs_xdr.h" ]; then
        if grep -q 'NFS_MAX_FILE_IO_SIZE.*(1048576U)' "${S}/include/linux/nfs_xdr.h"; then
            sed -i 's/NFS_MAX_FILE_IO_SIZE\t(1048576U)/NFS_MAX_FILE_IO_SIZE\t(8192U)/' "${S}/include/linux/nfs_xdr.h"
            bbnote "kernel-fixups: applied NFS max-rwsize 8K fix"
        fi
    fi

    # GCC 14+ -Werror=address: mem_section array address is never NULL
    # In non-SPARSEMEM_EXTREME mode, mem_section is a 2D array whose
    # elements are never NULL.  Wrap the NULL check so it only applies
    # when SPARSEMEM_EXTREME is enabled (pointer array, can be NULL).
    MMZONE="${S}/include/linux/mmzone.h"
    if [ -f "$MMZONE" ] && grep -q 'if (!mem_section\[SECTION_NR_TO_ROOT' "$MMZONE"; then
        sed -i '/if (!mem_section\[SECTION_NR_TO_ROOT/{
            i\#ifdef CONFIG_SPARSEMEM_EXTREME
            n
            a\#endif
        }' "$MMZONE"
        bbnote "kernel-fixups: wrapped mem_section NULL check in SPARSEMEM_EXTREME guard"
    fi

    # log2 tools fix: same as include/linux/log2.h but for tools/ copy
    if [ -f "${S}/tools/include/linux/log2.h" ]; then
        if grep -q '____ilog2_NaN' "${S}/tools/include/linux/log2.h"; then
            sed -i '/extern __attribute__((const, noreturn))/,/____ilog2_NaN(void);/d' "${S}/tools/include/linux/log2.h"
            sed -i 's/____ilog2_NaN()/1/' "${S}/tools/include/linux/log2.h"
            bbnote "kernel-fixups: applied log2 tools fix"
        fi
    fi
}
