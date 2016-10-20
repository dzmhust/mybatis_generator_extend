package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 生成自定义的example
 * 相对于ExampleWhereClauseElementGenerator中做了如下调整
 * 	1  去掉where节点、
 * 	2  去掉trim节点
 *  3 增加条件别名
 *  4 增加oredCriteria的别名
 *  示例如下：
 *    <sql id="Example_Where_Clause_Udf">
 *     <foreach collection="smsOrderExample.oredCriteria" item="criteria" separator="or">
 *       <if test="criteria.valid">
 *           <foreach collection="criteria.criteria" item="criterion">
 *             <choose>
 *               <when test="criterion.noValue">
 *                 and smsOrder.${criterion.condition}
 *               </when>
 *               <when test="criterion.singleValue">
 *                 and smsOrder.${criterion.condition} #{criterion.value}
 *               </when>
 *               <when test="criterion.betweenValue">
 *                 and smsOrder.${criterion.condition} #{criterion.value} and #{criterion.secondValue}
 *               </when>
 *               <when test="criterion.listValue">
 *                 and smsOrder.${criterion.condition}
 *                 <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
 *                   #{listItem}
 *                 </foreach>
 *               </when>
 *             </choose>
 *           </foreach>
 *       </if>
 *     </foreach>
 * </sql>
 * @author dzm
 *
 */
public class UdfExampleWhereClauseElementGenerator extends AbstractXmlElementGenerator{
	
	public UdfExampleWhereClauseElementGenerator(){
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		
		String tableAlias = StringUtility.underlineToCame(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		
		XmlElement answer = new XmlElement("sql");
		answer.addAttribute(new Attribute(
                "id", introspectedTable.getUdfExampleWhereClauseId())); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

//        XmlElement whereElement = new XmlElement("where"); //$NON-NLS-1$
//        answer.addElement(whereElement);

        XmlElement outerForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
        outerForEachElement.addAttribute(new Attribute(
                "collection", tableAlias + "Example.oredCriteria")); //$NON-NLS-1$ //$NON-NLS-2$
        outerForEachElement.addAttribute(new Attribute("item", "criteria")); //$NON-NLS-1$ //$NON-NLS-2$
        outerForEachElement.addAttribute(new Attribute("separator", "or")); //$NON-NLS-1$ //$NON-NLS-2$
//        whereElement.addElement(outerForEachElement);
        answer.addElement(outerForEachElement);

        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "criteria.valid")); //$NON-NLS-1$ //$NON-NLS-2$
        outerForEachElement.addElement(ifElement);

//        XmlElement trimElement = new XmlElement("trim"); //$NON-NLS-1$
//        trimElement.addAttribute(new Attribute("prefix", "(")); //$NON-NLS-1$ //$NON-NLS-2$
//        trimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
//        trimElement.addAttribute(new Attribute("prefixOverrides", "and")); //$NON-NLS-1$ //$NON-NLS-2$

//        ifElement.addElement(trimElement);

//        trimElement.addElement(getMiddleForEachElement(null));
        ifElement.addElement(getMiddleForEachElement(null, tableAlias));

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getNonBLOBColumns()) {
            if (stringHasValue(introspectedColumn
                    .getTypeHandler())) {
//                trimElement
//                        .addElement(getMiddleForEachElement(introspectedColumn));
            	ifElement.addElement(getMiddleForEachElement(introspectedColumn, tableAlias));
            }
        }

        if (context.getPlugins()
                .sqlMapExampleWhereClauseElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
		
	}

	
	private XmlElement getMiddleForEachElement(
            IntrospectedColumn introspectedColumn, String tableAlias) {
        StringBuilder sb = new StringBuilder();
        String criteriaAttribute;
        boolean typeHandled;
        String typeHandlerString;
        if (introspectedColumn == null) {
            criteriaAttribute = "criteria.criteria"; //$NON-NLS-1$
            typeHandled = false;
            typeHandlerString = null;
        } else {
            sb.setLength(0);
            sb.append("criteria."); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append("Criteria"); //$NON-NLS-1$
            criteriaAttribute = sb.toString();

            typeHandled = true;

            sb.setLength(0);
            sb.append(",typeHandler="); //$NON-NLS-1$
            sb.append(introspectedColumn.getTypeHandler());
            typeHandlerString = sb.toString();
        }

        XmlElement middleForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
        middleForEachElement.addAttribute(new Attribute(
                "collection", criteriaAttribute)); //$NON-NLS-1$
        middleForEachElement.addAttribute(new Attribute("item", "criterion")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement chooseElement = new XmlElement("choose"); //$NON-NLS-1$
        middleForEachElement.addElement(chooseElement);

        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.noValue")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new TextElement("and " + tableAlias + ".${criterion.condition}")); //$NON-NLS-1$
        chooseElement.addElement(when);

        when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.singleValue")); //$NON-NLS-1$ //$NON-NLS-2$
        sb.setLength(0);
        sb.append("and " + tableAlias + ".${criterion.condition} #{criterion.value"); //$NON-NLS-1$
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        when.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(when);

        when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.betweenValue")); //$NON-NLS-1$ //$NON-NLS-2$
        sb.setLength(0);
        sb.append("and " + tableAlias + ".${criterion.condition} #{criterion.value"); //$NON-NLS-1$
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append("} and #{criterion.secondValue"); //$NON-NLS-1$
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        when.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(when);

        when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.listValue")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new TextElement("and " + tableAlias + ".${criterion.condition}")); //$NON-NLS-1$
        XmlElement innerForEach = new XmlElement("foreach"); //$NON-NLS-1$
        innerForEach
                .addAttribute(new Attribute("collection", "criterion.value")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("item", "listItem")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("separator", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        sb.setLength(0);
        sb.append("#{listItem"); //$NON-NLS-1$
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        innerForEach.addElement(new TextElement(sb.toString()));
        when.addElement(innerForEach);
        chooseElement.addElement(when);

        return middleForEachElement;
    }
}
