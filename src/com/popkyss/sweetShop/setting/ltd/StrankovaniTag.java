package com.popkyss.sweetShop.setting.ltd;
/*     */ import java.io.IOException;

/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.ASeznamAction;
import com.popkyss.sweetShop.setting.HtmlSelect;
import com.popkyss.sweetShop.setting.PropertyReader;
import com.popkyss.sweetShop.setting.StrankovanySeznam;

/*     */ import net.sourceforge.stripes.tag.HtmlTagSupport;
/*     */ import net.sourceforge.stripes.util.UrlBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StrankovaniTag
/*     */   extends HtmlTagSupport
/*     */ {
/*     */   private ASeznamAction<?, ?> controller;
/*     */   
/*     */   private enum TypAkceStrankovani
/*     */   {
/*  26 */     PRVNI_STRANKA
/*     */     {
/*     */       public String getPageString(StrankovanySeznam<?> seznam) {
/*  29 */         return "1";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getAltText() {
/*  34 */         return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt1");
/*     */       }
/*     */ 
/*     */       
/*     */       public String getImageClass() {
/*  39 */         return "pagingHome";
/*     */       }
/*     */     },
/*  42 */     PREDCHOZI_STRANKA
/*     */     {
/*     */       public String getPageString(StrankovanySeznam<?> seznam) {
/*  45 */         return seznam.getPredchazejiciStrankaString();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getAltText() {
/*  50 */         return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt2");
/*     */       }
/*     */ 
/*     */       
/*     */       public String getImageClass() {
/*  55 */         return "pagingLeft";
/*     */       }
/*     */     },
/*  58 */     DALSI_STRANKA
/*     */     {
/*     */       public String getPageString(StrankovanySeznam<?> seznam) {
/*  61 */         return seznam.getNasledujiciStrankaString();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getAltText() {
/*  66 */         return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt3");
/*     */       }
/*     */ 
/*     */       
/*     */       public String getImageClass() {
/*  71 */         return "pagingRight";
/*     */       }
/*     */     },
/*  74 */     POSLEDNI_STRANKA
/*     */     {
/*     */       public String getPageString(StrankovanySeznam<?> seznam) {
/*  77 */         return seznam.getPosledniStrankaString();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getAltText() {
/*  82 */         return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt4");
/*     */       }
/*     */ 
/*     */       
/*     */       public String getImageClass() {
/*  87 */         return "pagingEnd";
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     public abstract String getPageString(StrankovanySeznam<?> param1StrankovanySeznam);
/*     */ 
/*     */     
/*     */     public abstract String getAltText();
/*     */ 
/*     */     
/*     */     public abstract String getImageClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public int doEndTag() throws JspException {
/*     */     try {
/* 104 */       StrankovanySeznam<?> strankovani = this.controller.getSeznamBean().getStrankovanySeznam();
/* 105 */       if (strankovani != null) {
/* 106 */         writeNumPagesInfo(strankovani);
/* 107 */         writeNumRowsSelectBox(strankovani);
/* 108 */         writePagingNavigation(strankovani);
/*     */       } 
/*     */       
/* 111 */       return 6;
/* 112 */     } catch (IOException e) {
/* 113 */       throw new JspException("aaaa - spadlo to", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int doStartTag() throws JspException {
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeNumPagesInfo(StrankovanySeznam<?> strankovani) throws IOException {
/* 125 */     StringBuilder html = new StringBuilder(200);
/* 126 */     html.append("\n<div class=\"tablePagingLeft\"> \n");
/* 127 */     html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem1")) + " ");
/* 128 */     html.append("<strong>" + strankovani.getCelkemRadku() + "</strong> ");
/* 129 */     html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem2")) + " ( ");
/* 130 */     html.append(String.valueOf(strankovani.getCelkemStranek()) + " " + PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem3") + " )");
/* 131 */     html.append("</div>\n");
/*     */     
/* 133 */     getPageContext().getOut().write(html.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeNumRowsSelectBox(StrankovanySeznam<?> strankovani) throws IOException {
/* 138 */     StringBuilder html = new StringBuilder(500);
/* 139 */     HtmlSelect<String, String> hs = strankovani.getPoctyRadkuNaStrance();
/*     */     
/* 141 */     html.append("<div class=\"tablePagingRight\">");
/* 142 */     html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamZobrazitPolozek1")) + "\n");
/*     */     
/* 144 */     html.append("<select name=\"radkyNaStrance\" onchange=\"setRadkyNaStrance(this.options[this.selectedIndex].value);\" >\n");
/* 145 */     for (int i = 0; i < hs.size(); i++) {
/* 146 */       String selected = "";
/* 147 */       if (hs.isSelected(i)) {
/* 148 */         selected = String.valueOf(selected) + " selected=\"selected\" ";
/*     */       }
/* 150 */       if (!((String)hs.getKey(i)).equals("ALL")) {
/* 151 */         html.append("\t<option value=\"" + (String)hs.getKey(i) + "\"" + selected + ">");
/* 152 */         html.append(String.valueOf(hs.getValue(i)) + " " + PropertyReader.bundle("g.StrankovanySeznamRadky") + "</option> \n");
/*     */       } else {
/* 154 */         html.append("\t<option value=\"" + (String)hs.getKey(i) + "\"" + selected + ">");
/* 155 */         html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamRadkyAll")) + "</option> \n");
/*     */       } 
/*     */     } 
/* 158 */     html.append("</select> \n");
/* 159 */     html.append("</div> \n");
/*     */     
/* 161 */     getPageContext().getOut().write(html.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private void writePagingNavigation(StrankovanySeznam<?> strankovani) throws JspException, IOException {
/* 166 */     if (strankovani.isExistPrvniStranka()) {
/* 167 */       writeImageLink(TypAkceStrankovani.PRVNI_STRANKA, strankovani);
/*     */     }
/* 169 */     if (strankovani.isExistPredchazejiciStranka())
/* 170 */       writeImageLink(TypAkceStrankovani.PREDCHOZI_STRANKA, strankovani); 
/*     */     int i;
/* 172 */     for (i = -3; i < 1; i++) {
/* 173 */       if (strankovani.isExistPredchazejiciStranka(i)) {
/* 174 */         writeLink(String.valueOf(strankovani.getStranka() + i - 1), String.valueOf(strankovani.getStranka() + i - 1), false, (String)null);
/*     */       }
/*     */     } 
/* 177 */     if (strankovani.getStranka() > 0) {
/* 178 */       getPageContext().getOut().write("<strong>[" + strankovani.getStranka() + "]</strong>&nbsp;&nbsp; \n");
/*     */     }
/* 180 */     for (i = 0; i < 4; i++) {
/* 181 */       if (strankovani.isExistNasledujiciStranka(i)) {
/* 182 */         writeLink(String.valueOf(strankovani.getStranka() + i + 1), String.valueOf(strankovani.getStranka() + i + 1), false, (String)null);
/*     */       }
/*     */     } 
/* 185 */     if (strankovani.isExistNasledujiciStranka()) {
/* 186 */       writeImageLink(TypAkceStrankovani.DALSI_STRANKA, strankovani);
/*     */     }
/* 188 */     if (strankovani.isExistPosledniStranka()) {
/* 189 */       writeImageLink(TypAkceStrankovani.POSLEDNI_STRANKA, strankovani);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeImageLink(TypAkceStrankovani typAkce, StrankovanySeznam<?> seznam) throws JspException {
/* 195 */     String body = "<img src=\"images/sys_spacer.gif\" alt=\"" + typAkce.getAltText() + "\" class=\"" + 
/* 196 */       typAkce.getImageClass() + "\" />";
/* 197 */     writeLink(typAkce.getPageString(seznam), body, true, typAkce.getAltText());
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeLink(String paramValue, String body, boolean imageLink, String title) throws JspException {
/*     */     try {
/* 203 */       String afterLink = imageLink ? " &nbsp;&nbsp; \n" : " &nbsp; \n";
/* 204 */       String href = getLinkReference("nastaveniStrankyAction", "nextPage", paramValue);
/* 205 */       set("href", href);
/* 206 */       setTitle(title);
/*     */       
/* 208 */       writeOpenTag(getPageContext().getOut(), "a");
/* 209 */       getPageContext().getOut().write(body);
/* 210 */       writeCloseTag(getPageContext().getOut(), "a");
/* 211 */       getPageContext().getOut().write(afterLink);
/*     */       
/* 213 */       getAttributes().remove("href");
/* 214 */       setTitle(null);
/* 215 */     } catch (IOException e) {
/* 216 */       throw new JspException("Problem pri vytvareni odkazu ve strankovani", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLinkReference(String event, String paramName, String paramValue) {
/* 222 */     HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
/* 223 */     HttpServletResponse response = (HttpServletResponse)getPageContext().getResponse();
/*     */     
/* 225 */     String actionUrl = getActionBeanUrl(this.controller.getClass());
/* 226 */     UrlBuilder builder = new UrlBuilder(this.pageContext.getRequest().getLocale(), actionUrl, false);
/* 227 */     if (event != null) {
/* 228 */       builder.setEvent(event);
/*     */     }
/*     */     
/* 231 */     builder.addParameter(paramName, new Object[] { paramValue });
/*     */     
/* 233 */     String url = builder.toString();
/* 234 */     String contextPath = request.getContextPath();
/* 235 */     if (contextPath.length() > 1) {
/* 236 */       url = String.valueOf(contextPath) + url;
/*     */     }
/*     */     
/* 239 */     return response.encodeURL(url);
/*     */   }
/*     */ 
/*     */   
/*     */   public ASeznamAction<?, ?> getController() {
/* 244 */     return this.controller;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setController(ASeznamAction<?, ?> controller) {
/* 249 */     this.controller = controller;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/StrankovaniTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */