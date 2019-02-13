/*
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2018 Ricardo Mariaca and the Dynamic Reports Contributors
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.dynamicreports.report.builder;

import org.apache.commons.lang3.Validate;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.definition.JasperReportHandler;
import net.sf.dynamicreports.report.builder.barcode.BarcodeBuilders;
import net.sf.dynamicreports.report.builder.chart.ChartBuilders;
import net.sf.dynamicreports.report.builder.column.ColumnBuilders;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilders;
import net.sf.dynamicreports.report.builder.condition.ConditionBuilders;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilders;
import net.sf.dynamicreports.report.builder.datatype.DataTypeBuilders;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.expression.ExpressionBuilders;
import net.sf.dynamicreports.report.builder.grid.GridBuilders;
import net.sf.dynamicreports.report.builder.group.GroupBuilders;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilders;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsHeadingBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.OrderType;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * <p>DynamicReports class.</p>
 *
 * @author Ricardo Mariaca
 *
 */
public class DynamicReports {
    /**
     * A set of methods of creating report columns.<br/> It is used to display data in a multi-column layout.
     */
    public static final ColumnBuilders col = new ColumnBuilders();
    /**
     * A set of methods of customizing columns layout.
     */
    public static final GridBuilders grid = new GridBuilders();
    /**
     * A set of methods of creating report groups.
     */
    public static final GroupBuilders grp = new GroupBuilders();
    /**
     * A set of methods of creating column subtotals.
     */
    public static final SubtotalBuilders sbt = new SubtotalBuilders();
    /**
     * A set of methods of creating and customizing styles.
     */
    public static final StyleBuilders stl = new StyleBuilders();
    /**
     * A set of methods of creating components.
     */
    public static final ComponentBuilders cmp = new ComponentBuilders();
    /**
     * A set of build in expressions.<br/> Expressions are used to define various calculations, conditions, text field content, specific report groups, etc.
     */
    public static final ExpressionBuilders exp = new ExpressionBuilders();
    /**
     * A set of build in condition expressions.
     */
    public static final ConditionBuilders cnd = new ConditionBuilders();
    /**
     * A set of build in data types.
     */
    public static final DataTypeBuilders type = new DataTypeBuilders();
    /**
     * A set of methods of creating and customizing charts.
     */
    public static final ChartBuilders cht = new ChartBuilders();
    /**
     * A set of methods of creating exporters.
     */
    public static final ExporterBuilders export = new ExporterBuilders();
    /**
     * A set of methods of creating barcodes.
     */
    public static final BarcodeBuilders bcode = new BarcodeBuilders();
    /**
     * A set of methods of creating and customizing crosstabs.
     */
    public static final CrosstabBuilders ctab = new CrosstabBuilders();

    /**
     * Creates a new report builder. The most used report builder for creating reports. It allows constructing and customizing the whole report content.
     *
     * @return a report builder
     */
    public static JasperReportBuilder report() {
        return new JasperReportBuilder();
    }

    /**
     * Creates a new concatenated report builder. This report builder allows concatenating several separated reports into one single document.
     *
     * @return a report builder
     */
    public static JasperConcatenatedReportBuilder concatenatedReport() {
        return new JasperConcatenatedReportBuilder();
    }

    /**
     * Creates a new concatenated report builder. This report builder allows concatenating several separated reports into one single document.
     *
     * @param jasperReportHandler a {@link net.sf.dynamicreports.jasper.definition.JasperReportHandler} object.
     * @return a report builder
     */
    public static JasperConcatenatedReportBuilder concatenatedReport(final JasperReportHandler jasperReportHandler) {
        return new JasperConcatenatedReportBuilder(jasperReportHandler);
    }

    // field
    public static <T> FieldBuilder<T> field(String name, Class<T> valueClass) {
        FieldBuilder<T> fieldBuilder = new FieldBuilder<T>(name, valueClass);
        try {
            final DRIDataType<? super T, T> dataType = DataTypes.detectType(valueClass);
            fieldBuilder.setDataType(dataType);
        } catch (final DRException e) {
        }
        return fieldBuilder;
    }

    public static <T> FieldBuilder<T> field(String name, DRIDataType<? super T, T> dataType) {
        Validate.notNull(dataType, "dataType must not be null");
        final FieldBuilder<T> fieldBuilder = new FieldBuilder<T>(name, dataType.getValueClass());
        fieldBuilder.setDataType(dataType);
        return fieldBuilder;
    }

    // variable
    public static <T> VariableBuilder<T> variable(final ValueColumnBuilder<?, ?> column, final Calculation calculation) {
        Validate.notNull(column, "column must not be null");
        return new VariableBuilder<>(column, calculation);
    }

    public static <T> VariableBuilder<T> variable(final String name, final ValueColumnBuilder<?, ?> column, final Calculation calculation) {
        Validate.notNull(column, "column must not be null");
        return new VariableBuilder<>(name, column, calculation);
    }

    public static <T> VariableBuilder<T> variable(final FieldBuilder<T> field, final Calculation calculation) {
        Validate.notNull(field, "field must not be null");
        return new VariableBuilder<>(field, calculation);
    }

    public static <T> VariableBuilder<T> variable(final String name, final FieldBuilder<T> field, final Calculation calculation) {
        return new VariableBuilder<>(name, field, calculation);
    }

    public static <T> VariableBuilder<T> variable(final String fieldName, final Class<?> valueClass, final Calculation calculation) {
        return new VariableBuilder<>(field(fieldName, valueClass), calculation);
    }

    public static <T> VariableBuilder<T> variable(final String name, final String fieldName, final Class<?> valueClass, final Calculation calculation) {
        return new VariableBuilder<>(name, field(fieldName, valueClass), calculation);
    }

    public static <T> VariableBuilder<T> variable(final DRIExpression<?> expression, final Calculation calculation) {
        return new VariableBuilder<>(expression, calculation);
    }

    public static <T> VariableBuilder<T> variable(final String name, final DRIExpression<?> expression, final Calculation calculation) {
        return new VariableBuilder<>(name, expression, calculation);
    }

    // sort
    public static SortBuilder asc(final TextColumnBuilder<?> column) {
        return new SortBuilder(column).setOrderType(OrderType.ASCENDING);
    }

    public static SortBuilder asc(final FieldBuilder<?> field) {
        return new SortBuilder(field).setOrderType(OrderType.ASCENDING);
    }

    public static SortBuilder asc(final String fieldName, final Class<?> valueClass) {
        return new SortBuilder(field(fieldName, valueClass)).setOrderType(OrderType.ASCENDING);
    }

    public static SortBuilder asc(final VariableBuilder<?> variable) {
        return new SortBuilder(variable).setOrderType(OrderType.ASCENDING);
    }

    public static SortBuilder asc(final DRIExpression<?> expression) {
        return new SortBuilder(expression).setOrderType(OrderType.ASCENDING);
    }

    public static SortBuilder desc(final TextColumnBuilder<?> column) {
        return new SortBuilder(column).setOrderType(OrderType.DESCENDING);
    }

    public static SortBuilder desc(final FieldBuilder<?> field) {
        return new SortBuilder(field).setOrderType(OrderType.DESCENDING);
    }

    public static SortBuilder desc(final String fieldName, final Class<?> valueClass) {
        return new SortBuilder(field(fieldName, valueClass)).setOrderType(OrderType.DESCENDING);
    }

    public static SortBuilder desc(final VariableBuilder<?> variable) {
        return new SortBuilder(variable).setOrderType(OrderType.DESCENDING);
    }

    public static SortBuilder desc(final DRIExpression<?> expression) {
        return new SortBuilder(expression).setOrderType(OrderType.DESCENDING);
    }

    // hyperLink
    public static HyperLinkBuilder hyperLink() {
        return new HyperLinkBuilder();
    }

    public static HyperLinkBuilder hyperLink(final String link) {
        return new HyperLinkBuilder(link);
    }

    public static HyperLinkBuilder hyperLink(final DRIExpression<String> linkExpression) {
        return new HyperLinkBuilder(linkExpression);
    }

    // margin
    public static MarginBuilder margin() {
        return new MarginBuilder();
    }

    public static MarginBuilder margin(final int margin) {
        return new MarginBuilder(margin);
    }

    // parameter
    public static <T> ParameterBuilder<T> parameter(final String name, final T value) {
        return new ParameterBuilder<>(name, value);
    }

    public static <T> ParameterBuilder<T> parameter(final String name, final Class<T> valueClass) {
        return new ParameterBuilder<>(name, valueClass);
    }

    // query
    public static QueryBuilder query(final String text, final String language) {
        return new QueryBuilder(text, language);
    }

    // units
    public static int cm(final Number value) {
        return Units.cm(value);
    }

    public static int inch(final Number value) {
        return Units.inch(value);
    }

    public static int mm(final Number value) {
        return Units.mm(value);
    }

    // template
    public static ReportTemplateBuilder template() {
        return new ReportTemplateBuilder();
    }

    // table of contents
    public static TableOfContentsCustomizerBuilder tableOfContentsCustomizer() {
        return new TableOfContentsCustomizerBuilder();
    }

    public static TableOfContentsHeadingBuilder tableOfContentsHeading() {
        return new TableOfContentsHeadingBuilder();
    }

    public static TableOfContentsHeadingBuilder tableOfContentsHeading(final String label) {
        return new TableOfContentsHeadingBuilder().setLabel(label);
    }

    // dataset
    public static DatasetBuilder dataset() {
        return new DatasetBuilder();
    }
}
