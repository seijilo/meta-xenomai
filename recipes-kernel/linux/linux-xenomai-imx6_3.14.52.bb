# Adapted from linux-boundary in meta-fsl-arm-extras
# https://github.com/Freescale/meta-fsl-arm-extra

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux kernel for Boundary Devices boards with Xenomai support"

LOCALVERSION = "-1.1.0_ga+yocto+xeno"
SRCBRANCH = "boundary-imx_3.14.52_1.1.0_ga"
SRCREV = "a139368c83b5fffc073d4f95c93cf1c234d5d0f3"
COMPATIBLE_MACHINE = "(nitrogen6x|nitrogen6x-lite|nitrogen6sx)"

DEPENDS += "lzop-native bc-native"

SRC_URI = "git://github.com/boundarydevices/linux-imx6.git;branch=${SRCBRANCH} \
           file://defconfig \
           file://ipipe-core-3.14.44-arm-17-adapted-for-Linux-imx.patch;apply=0 \
           file://0001-fix-build-cpufreq.patch \
"

# Xenomai source (prepare_kernel.sh script)
SRC_URI += "http://xenomai.org/downloads/xenomai/stable/xenomai-3.0.2.tar.bz2;name=xeno"
SRC_URI[xeno.md5sum] = "bf0986db38f02174b2cd55ec6ed2f90b"
SRC_URI[xeno.sha256sum] = "731fbf720c4e6c31fe00a668bb9b2659a5dce06ee8ba684c14f8b2da8d4957bf"

do_prepare_kernel () {
    # Set linux kernel source directory
    linux_src="${WORKDIR}/git"

    # Set xenomai source directory 
    xenomai_src="${WORKDIR}/xenomai-3.0.2"

    # Set ipipe patch (adapted for linux-imx kernel)
    ipipe_patch="${WORKDIR}/ipipe-core-3.14.44-arm-17-adapted-for-Linux-imx.patch"

    # Prepare kernel
    "${xenomai_src}"/scripts/prepare-kernel.sh --arch=arm --linux="${linux_src}" --ipipe="${ipipe_patch}" --default
}

addtask prepare_kernel after do_patch before do_configure

