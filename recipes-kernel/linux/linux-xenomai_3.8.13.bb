SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
DESCRIPTION = "Linux kernel with Xenomai patch"

inherit kernel siteinfo

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI[kernel.md5sum] = "2af19d06cd47ec459519159cdd10542d"
SRC_URI[kernel.sha256sum] = "7cd135cc1791680553cc40bf23ce11ab24b84a3ece33d810950e089090d4f65f"

SRC_URI[xeno.md5sum] = "9f83c39cfb10535df6bf51702714e716"
SRC_URI[xeno.sha256sum] = "4d0d09431f0340cf4c9e2745177f77f15b7b124f89982035d1d3586519d7afe9"

# Linux kernel
SRC_URI += "http://www.kernel.org/pub/linux/kernel/v3.x/linux-3.8.13.tar.xz;name=kernel"

#xenomai source (for prepare_kernel.sh script)
SRC_URI += "http://download.gna.org/xenomai/stable/xenomai-2.6.3.tar.bz2;name=xeno"

#kernel defconfig
SRC_URI +=  "file://defconfig"

do_prepare_kernel () {
    #set linux kernel source directory
    linux_src="${S}"

    #set xenomai source directory 
    xenomai_src="${TMPDIR}/work/${TARGET_ARCH}-poky-${TARGET_OS}/${PN}/${EXTENDPE}${PV}-${PR}/xenomai-2.6.3/"

    #prepare kernel
    $xenomai_src/scripts/prepare-kernel.sh --arch=x86 --linux=$linux_src --default
}

addtask prepare_kernel after do_patch before do_configure

S = "${WORKDIR}/linux-3.8.13"
