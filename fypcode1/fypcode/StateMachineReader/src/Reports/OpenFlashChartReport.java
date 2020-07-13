/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca
 * http://www.dynamicreports.org
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package Reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.base.AbstractScriptlet;
import net.sf.dynamicreports.report.builder.component.GenericElementBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class OpenFlashChartReport {
	private PieChart pieChart1;
	private PieChart pieChart2;
	/*private String name;
	private String category;
	private String pass;*/
	//private ArrayList<String> values;
	ArrayList<ArrayList<String>> values;

	public OpenFlashChartReport(ArrayList<ArrayList<String>> values_) {
		values = values_;
		/*name="sampe";
		category="hey";
		pass="idio";*/
		build();
	}
	/*public OpenFlashChartReport() {
		values = new ArrayList<ArrayList<String>>();
		name="sampe";
		category="hey";
		pass="idio";
		//build();
	}*/

	private void build() {


		try {
			JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("C:/Users/ranahamzaintisar/fypcode1/fypcode1/fypcode/report.html")
				.setImagesDirName("C:/Users/ranahamzaintisar/fypcode1/fypcode1/fypcode/images")
				.setOutputImagesToDir(true);

			report()
				.setTemplate(Templates.reportTemplate)
				.scriptlets(new ReportScriptlet())
				.columns(
					col.column("Transition Name", "name", type.stringType()),
					col.column("Type", "category", type.stringType()),
					col.column("Pass/Fail", "pass", type.stringType()))
				.title(Templates.createTitleComponent("AGTT Report"))
				//.summary(
					//cmp.horizontalList(chart1, chart2))
				.setDataSource(getValues())
				.toHtml(htmlExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public JRDataSource getValues() {
		DRDataSource dataSource = new DRDataSource("name", "category", "pass");
		for(int i=0;i<values.size();i++){
			String name=values.get(i).get(0);
			String category=values.get(i).get(1);
			String pass=values.get(i).get(2);
			dataSource.add(name, category, pass);
			System.out.println(name+","+category+","+pass);
			//System.out.println(values.get(i));
			//dataSource.add("ObstructionVisibleForward", "System Generated", "Fail");
			//dataSource.add("PDA", "User", "Pass");
		}
		return dataSource;
	}
	
	private class ReportScriptlet extends AbstractScriptlet {

		@Override
		public void afterDetailEval(ReportParameters reportParameters) {
			super.afterDetailEval(reportParameters);
			String name = reportParameters.getValue("name");
			String category = reportParameters.getValue("category");
			String pass = reportParameters.getValue("pass");
			
			//pieChart1.addValue(item, quantity);
			//pieChart2.addValue(item, unitPrice);
		}
	}

	/*public static void main(String[] args) {
		new OpenFlashChartReport();
	}*/
}
