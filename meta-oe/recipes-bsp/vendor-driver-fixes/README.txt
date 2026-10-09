Build-time vendor driver fixes
=============================

Enabled only by these recipes:
  gigablue-platform-util-gb7252pro: dvb_init judder, DVB ACM/gamma, Nexus HLG
  vuplus-platform-util-vuduo4klite: dvb_init judder, DVB ACM/gamma, Nexus HLG
  vuplus-dvb-proxy-vuultimo4k: DVB gamma

The recipes require vendor-driver-fixes.inc and select a fix profile.
do_install first installs the vendor originals, then the append patches ${D}.
Downloads, their checksums and files under ${S} remain unchanged. Each repeated
install starts from the originals, including for the non-idempotent dvb_init fix.
Python runs on the build host through python3native, never on the receiver.
An unsuccessful patch aborts the task; original/output hashes are in the log.
No Patcher scripts are installed in the resulting image.

The Lite/Pro init scripts already copy the package's /home/root/platform files
to the runtime locations on first boot. Patch that package source once rather
than distributing loose copies of modules to several image paths.

Ultimo's package revision has a local .1 suffix. The shared DVB proxy download
URL uses SRCDATE.SRCDATE_PR instead of PR so package revisions do not change the
vendor archive name. Other proxy recipes retain their current download names.

Supplied patchers, retained unchanged for auditing:
  patch_dvb_init.py, patch_dvb_acm.py:
    from "lite and pro patched drivers.rar"
  patch_dvb_gamma.py:
    from "pro - lite -patch_dvb_gamma (2).py"
  patch_nexus_hlg.py:
    from "patch_nexus_hlg.py"
  patch_dvb_bcm7444_gamma.py:
    from "patch_dvb_bcm7444_gamma Ultimo4K.py"

The newer gamma scripts report new streams even when their gamma is unchanged.
Their already-patched detection does not distinguish the older gamma variant;
always run this integration on freshly installed original vendor files.
The optional VideoEnhancement pep_split interface is repurposed as gamma.
Gamma proc reporting in these patchers covers vmpeg/0, not additional decoders.
The unrelated Duo ZIP is deliberately excluded.

Validation on 2026-10-09
------------------------
All three recipes parsed with the buildhost's BitBake parser and native Python
dependency. Source URLs and file search paths were checked. The expanded
do_install shell bodies were executed twice for each recipe against extracted
vendor archives, including existing config/stcbind installation steps.
All seven output hashes matched the previously verified reference outputs;
all unpacked originals stayed unchanged. Unsupported input caused a fatal task
failure. This validates installation integration, not a complete image build or
runtime hardware behavior of the combined fixes.

Archive SHA256:
  Pro 20260807.r0:
    14641bc40d2ad014b2419bb46939973a827683bae51bf0f493da7b8fe7403325
  Lite 20260911:
    239dc10f9abdce9d963bd2e94cc0111bfb3ee2d545e501f62a1b18d9dd7de1f8
  Ultimo 20250706.r2:
    bb10691d361812bcfb9eaea541ae85f2a72d3956ab74c64406088583b39e818a

Output SHA256:
  Pro dvb_init:
    9577d2baa29325ca68d934ee76394b5eca4dd7e27aa4d316d9d76ff63af6c5d8
  Pro dvb.ko (ACM + new gamma):
    c9a9265fad2bfbe41ecd973527d43ffb47ac6ac4d1625eaf2bedf1b323992bda
  Pro nexus.ko:
    f89e0b3a536262eba056615f5ec7f63c84e41a1b7010a1a935c6ca22b1202fac
  Lite dvb_init:
    100db6f42e482466dc0a397956a0c8496c4ef7cec2c65543ffac4ca8390b7541
  Lite dvb.ko (ACM + new gamma):
    2b1ed1b9f3544d752521aa37c4a6abab3df8fc8534c83b26f132a21d3baec781
  Lite nexus.ko:
    35fb7b02cae2880a5081713d6d819573667829e897db2559fe69e65b5e34cc81
  Ultimo dvb-bcm7444.ko (new gamma, no debug):
    a9b6d406ddc993cc5d277e055b7916178d5b60b1e90c24cd63da96a21db1e67f

For subsequent vendor updates, rerun do_install for each configured machine,
inspect log.do_install and test the hardware. A changed instruction pattern
must be reviewed before accepting or modifying a patcher. Do not downgrade a
patch failure to a warning or substitute a module from another receiver.
