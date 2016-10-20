package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 产生定义的ResultMapWithoutBLOBs
 * 	1.增加column的别名
 *   <resultMap id="BaseResultMapUdf" type="com.dzmsoft.sms.base.pojo.SmsEmployee">
 *   <id column="id_smsEmployee" jdbcType="CHAR" property="id" />
 *   <result column="name_smsEmployee" jdbcType="VARCHAR" property="name" />
 *   <result column="sex_smsEmployee" jdbcType="CHAR" property="sex" />
 *   <result column="birthday_smsEmployee" jdbcType="DATE" property="birthday" />
 * </resultMap>
 * @author dzm
 *
 */
public class UdfResultMapWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator{
	
	public UdfResultMapWithoutBLOBsElementGenerator(){
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {

        XmlElement answer = new XmlElement("resultMap"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getUdfResultMapWithBLOBsId()));

        String returnType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            returnType = introspectedTable.getRecordWithBLOBsType();
        } else {
            // table has BLOBs, but no BLOB class - BLOB fields must be
            // in the base class
            returnType = introspectedTable.getBaseRecordType();
        }

        answer.addAttribute(new Attribute("type", //$NON-NLS-1$
                returnType));

        context.getCommentGenerator().addComment(answer);

        addResultMapElements(answer);

        if (context.getPlugins()
                .sqlMapResultMapWithBLOBsElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    
		
	}
	
	private void addResultMapElements(XmlElement answer) {
		String tableAlias = StringUtility.underlineToCame(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		String column = null;
		for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            XmlElement resultElement = new XmlElement("id"); //$NON-NLS-1$
            column = MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn);
            resultElement.addAttribute(new Attribute("column", column+"_"+tableAlias)); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute(
                    "property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn
                    .getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            answer.addElement(resultElement);
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getBaseColumns()) {
            XmlElement resultElement = new XmlElement("result"); //$NON-NLS-1$
            column = MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn);
            resultElement
                    .addAttribute(new Attribute(
                            "column", column+"_"+tableAlias)); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute(
                    "property", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            resultElement.addAttribute(new Attribute("jdbcType", //$NON-NLS-1$
                    introspectedColumn.getJdbcTypeName()));

            if (stringHasValue(introspectedColumn
                    .getTypeHandler())) {
                resultElement.addAttribute(new Attribute(
                        "typeHandler", introspectedColumn.getTypeHandler())); //$NON-NLS-1$
            }

            answer.addElement(resultElement);
        }
    }


}
