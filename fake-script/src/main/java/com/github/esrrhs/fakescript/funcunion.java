package com.github.esrrhs.fakescript;

class funcunion
{
	public fkfunctor m_ff;
	public bifunc m_bif;
	public func_binary m_fb;
	public boolean m_haveff;
	public boolean m_havebif;
	public boolean m_havefb;

	public funcunion clonef()
	{
		funcunion fc = new funcunion();
		fc.m_ff = this.m_ff;
		fc.m_bif = this.m_bif;
		fc.m_fb = this.m_fb != null ? this.m_fb.clonef() : null;
		fc.m_haveff = this.m_haveff;
		fc.m_havebif = this.m_havebif;
		fc.m_havefb = this.m_havefb;
		return fc;
	}
}
