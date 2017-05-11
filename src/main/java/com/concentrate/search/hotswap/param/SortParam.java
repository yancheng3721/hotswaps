package com.concentrate.search.hotswap.param;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 16个预留字段，根据频道定制相关排序字段含义
 * @author 14060329
 *
 */
public class SortParam {

	public float f1 = 0.0f;
	public float f2 = 0.0f;
	public float f3 = 0.0f;
	public float f4 = 0.0f;
	public float f5 = 0.0f;
	public float f6 = 0.0f;
	public float f7 = 0.0f;
	public float f8 = 0.0f;
	public float f9 = 0.0f;
	public float f10 = 0.0f;
	public float f11 = 0.0f;
	public float f12 = 0.0f;
	public float f13 = 0.0f;
	public float f14 = 0.0f;
	public float f15 = 0.0f;
	public float f16 = 0.0f;
	public float f17 = 0.0f;
	public float f18 = 0.0f;
	public float f19 = 0.0f;
	public float f20 = 0.0f;
	public float f21 = 0.0f;
	public float f22 = 0.0f;
	public float f23 = 0.0f;
	public float f24 = 0.0f;
	public float f25 = 0.0f;
	public float f26 = 0.0f;
	public float f27 = 0.0f;
	public float f28 = 0.0f;
	public float f29 = 0.0f;
	public float f30 = 0.0f;
	public float f31 = 0.0f;
	public float f32 = 0.0f;
	public float f33 = 0.0f;
	public float f34 = 0.0f;
	public float f35 = 0.0f;
	public float f36 = 0.0f;
	public float f37 = 0.0f;
	public float f38 = 0.0f;
	public float f39 = 0.0f;
	public float f40 = 0.0f;
	public float f41 = 0.0f;
	public float f42 = 0.0f;
	public float f43 = 0.0f;
	public float f44 = 0.0f;
	public float f45 = 0.0f;
	public float f46 = 0.0f;
	public float f47 = 0.0f;
	public float f48 = 0.0f;
	public float f49 = 0.0f;
	public float f50 = 0.0f;
	
	public static void sortField(List<String> fields) {
        Collections.sort(fields, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				try {
					int a = Integer.valueOf(o1.substring(1));
					int b = Integer.valueOf(o2.substring(1));
					return a-b;
				} catch(Exception e) {
					return 0;
				}

			}
    	});
	}
	
	public float getF1() {
		return f1;
	}
	public void setF1(float f1) {
		this.f1 = f1;
	}
	public float getF2() {
		return f2;
	}
	public void setF2(float f2) {
		this.f2 = f2;
	}
	public float getF3() {
		return f3;
	}
	public void setF3(float f3) {
		this.f3 = f3;
	}
	public float getF4() {
		return f4;
	}
	public void setF4(float f4) {
		this.f4 = f4;
	}
	public float getF5() {
		return f5;
	}
	public void setF5(float f5) {
		this.f5 = f5;
	}
	public float getF6() {
		return f6;
	}
	public void setF6(float f6) {
		this.f6 = f6;
	}
	public float getF7() {
		return f7;
	}
	public void setF7(float f7) {
		this.f7 = f7;
	}
	public float getF8() {
		return f8;
	}
	public void setF8(float f8) {
		this.f8 = f8;
	}
	public float getF9() {
		return f9;
	}
	public void setF9(float f9) {
		this.f9 = f9;
	}
	public float getF10() {
		return f10;
	}
	public void setF10(float f10) {
		this.f10 = f10;
	}
	public float getF11() {
		return f11;
	}
	public void setF11(float f11) {
		this.f11 = f11;
	}
	public float getF12() {
		return f12;
	}
	public void setF12(float f12) {
		this.f12 = f12;
	}
	public float getF13() {
		return f13;
	}
	public void setF13(float f13) {
		this.f13 = f13;
	}
	public float getF14() {
		return f14;
	}
	public void setF14(float f14) {
		this.f14 = f14;
	}
	public float getF15() {
		return f15;
	}
	public void setF15(float f15) {
		this.f15 = f15;
	}
	public float getF16() {
		return f16;
	}
	public void setF16(float f16) {
		this.f16 = f16;
	}
	public float getF17() {
		return f17;
	}

	public void setF17(float f17) {
		this.f17 = f17;
	}

	public float getF18() {
		return f18;
	}

	public void setF18(float f18) {
		this.f18 = f18;
	}

	public float getF19() {
		return f19;
	}

	public void setF19(float f19) {
		this.f19 = f19;
	}

	public float getF20() {
		return f20;
	}

	public void setF20(float f20) {
		this.f20 = f20;
	}

	public float getF21() {
		return f21;
	}

	public void setF21(float f21) {
		this.f21 = f21;
	}

	public float getF22() {
		return f22;
	}

	public void setF22(float f22) {
		this.f22 = f22;
	}

	public float getF23() {
		return f23;
	}

	public void setF23(float f23) {
		this.f23 = f23;
	}

	public float getF24() {
		return f24;
	}

	public void setF24(float f24) {
		this.f24 = f24;
	}

	public float getF25() {
		return f25;
	}

	public void setF25(float f25) {
		this.f25 = f25;
	}

	public float getF26() {
		return f26;
	}

	public void setF26(float f26) {
		this.f26 = f26;
	}

	public float getF27() {
		return f27;
	}

	public void setF27(float f27) {
		this.f27 = f27;
	}

	public float getF28() {
		return f28;
	}

	public void setF28(float f28) {
		this.f28 = f28;
	}

	public float getF29() {
		return f29;
	}

	public void setF29(float f29) {
		this.f29 = f29;
	}

	public float getF30() {
		return f30;
	}

	public void setF30(float f30) {
		this.f30 = f30;
	}

	public float getF31() {
		return f31;
	}

	public void setF31(float f31) {
		this.f31 = f31;
	}

	public float getF32() {
		return f32;
	}

	public void setF32(float f32) {
		this.f32 = f32;
	}

	public float getF33() {
		return f33;
	}

	public void setF33(float f33) {
		this.f33 = f33;
	}

	public float getF34() {
		return f34;
	}

	public void setF34(float f34) {
		this.f34 = f34;
	}

	public float getF35() {
		return f35;
	}

	public void setF35(float f35) {
		this.f35 = f35;
	}

	public float getF36() {
		return f36;
	}

	public void setF36(float f36) {
		this.f36 = f36;
	}

	public float getF37() {
		return f37;
	}

	public void setF37(float f37) {
		this.f37 = f37;
	}

	public float getF38() {
		return f38;
	}

	public void setF38(float f38) {
		this.f38 = f38;
	}

	public float getF39() {
		return f39;
	}

	public void setF39(float f39) {
		this.f39 = f39;
	}

	public float getF40() {
		return f40;
	}

	public void setF40(float f40) {
		this.f40 = f40;
	}

	public float getF41() {
		return f41;
	}

	public void setF41(float f41) {
		this.f41 = f41;
	}

	public float getF42() {
		return f42;
	}

	public void setF42(float f42) {
		this.f42 = f42;
	}

	public float getF43() {
		return f43;
	}

	public void setF43(float f43) {
		this.f43 = f43;
	}

	public float getF44() {
		return f44;
	}

	public void setF44(float f44) {
		this.f44 = f44;
	}

	public float getF45() {
		return f45;
	}

	public void setF45(float f45) {
		this.f45 = f45;
	}

	public float getF46() {
		return f46;
	}

	public void setF46(float f46) {
		this.f46 = f46;
	}

	public float getF47() {
		return f47;
	}

	public void setF47(float f47) {
		this.f47 = f47;
	}

	public float getF48() {
		return f48;
	}

	public void setF48(float f48) {
		this.f48 = f48;
	}

	public float getF49() {
		return f49;
	}

	public void setF49(float f49) {
		this.f49 = f49;
	}

	public float getF50() {
		return f50;
	}

	public void setF50(float f50) {
		this.f50 = f50;
	}

	@Override
	public String toString() {
		return "SortParam [f1=" + f1 + ", f2=" + f2 + ", f3=" + f3 + ", f4="
				+ f4 + ", f5=" + f5 + ", f6=" + f6 + ", f7=" + f7 + ", f8="
				+ f8 + ", f9=" + f9 + ", f10=" + f10 + ", f11=" + f11
				+ ", f12=" + f12 + ", f13=" + f13 + ", f14=" + f14 + ", f15="
				+ f15 + ", f16=" + f16 + ", f17=" + f17 + ", f18=" + f18
				+ ", f19=" + f19 + ", f20=" + f20 + ", f21=" + f21 + ", f22="
				+ f22 + ", f23=" + f23 + ", f24=" + f24 + ", f25=" + f25
				+ ", f26=" + f26 + ", f27=" + f27 + ", f28=" + f28 + ", f29="
				+ f29 + ", f30=" + f30 + ", f31=" + f31 + ", f32=" + f32
				+ ", f33=" + f33 + ", f34=" + f34 + ", f35=" + f35 + ", f36="
				+ f36 + ", f37=" + f37 + ", f38=" + f38 + ", f39=" + f39
				+ ", f40=" + f40 + ", f41=" + f41 + ", f42=" + f42 + ", f43="
				+ f43 + ", f44=" + f44 + ", f45=" + f45 + ", f46=" + f46
				+ ", f47=" + f47 + ", f48=" + f48 + ", f49=" + f49 + ", f50="
				+ f50 + "]";
	}
}
