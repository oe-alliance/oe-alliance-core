/*
 * AArch64 compatibility helpers for ChromeOS Widevine CDM binaries.
 *
 * ChromeOS links these out-of-line LSE atomic helpers from libgcc.a into its
 * browser. They are intentionally not exported by normal libgcc_s.so.1. These
 * ARMv8.0 LL/SC implementations work without optional LSE CPU instructions.
 */

	.text
	.p2align 2

	.global __aarch64_ldadd4_acq_rel
	.type __aarch64_ldadd4_acq_rel, %function
__aarch64_ldadd4_acq_rel:
	mov	w16, w0
1:
	ldaxr	w0, [x1]
	add	w17, w0, w16
	stlxr	w15, w17, [x1]
	cbnz	w15, 1b
	ret
	.size __aarch64_ldadd4_acq_rel, .-__aarch64_ldadd4_acq_rel

	.global __aarch64_swp4_acq_rel
	.type __aarch64_swp4_acq_rel, %function
__aarch64_swp4_acq_rel:
	mov	w16, w0
1:
	ldaxr	w0, [x1]
	stlxr	w17, w16, [x1]
	cbnz	w17, 1b
	ret
	.size __aarch64_swp4_acq_rel, .-__aarch64_swp4_acq_rel

	.section .note.GNU-stack,"",%progbits
