package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 生成自定义的BaseColumnList
 * 相对于BaseColumnList,调整如下：
 * 1. 每个字段都增加表名的引用
 * 2. 每个字段都增加别名
 * 	示例如下：
 * <sql id="Base_Column_List_Udf">
 *   smsOrder.id id_smsOrder, smsOrder.order_time order_time_smsOrder, smsOrder.appointment_time appointment_time_smsOrder, smsOrder.director director_smsOrder, smsOrder.status status_smsOrder, smsOrder.member member_smsOrder
 *   , smsOrder.address address_smsOrder, smsOrder.individual_needs individual_needs_smsOrder, 
 *   smsOrder.order_type order_type_smsOrder, smsOrder.member_phone member_phone_smsOrder,smsOrder.member_name member_name_smsOrder
 * </sql>
 * @author dzm
 *
 */
public class UdfBaseColumnListElementGenerator extends AbstractXmlElementGenerator{
	
	public UdfBaseColumnListElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		
		String tableAlias = StringUtility.underlineToCame(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getUdfBaseColumnListId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable
                .getNonBLOBColumns().iterator();
        String column = null;
        while (iter.hasNext()) {
        	column = MyBatis3FormattingUtilities.getSelectListPhrase(iter.next());
            sb.append(tableAlias).append(".").append(column).append(" ").append(column).append("_").append(tableAlias);

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }

        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
		
	}

}
