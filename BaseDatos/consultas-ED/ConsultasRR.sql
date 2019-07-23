--Esta solo sacan docentes
Select DISTINCT (pro_codigo) as matp_profesor, per_primerapellido, per_segundoapellido, per_primernombre, per_segundonombre,  '0'as matp_periodolectivo,pro_codigo as matp_profesor, per_identificacion from personas, profesores,materias,carreras
where personas.per_codigo= profesores.pro_persona
and carreras.car_codigo= materias.mat_carrera order by per_primerapellido




--No tenemos estas tablas 
SELECT grup_de_eval.profe_evaluador_cod,
grup_de_eval.prof_nom_ape_evaluador,grup_de_eval.profe_evaluado_cod,
grup_de_eval.prof_nom_ape_evaluado,
proyectos.proyec_nom,grup_de_eval.cod_car, grup_de_eval.unidad_orga_cod,grup_de_eval.compo_cod,unidad_orga_nom, unidad_organi.tipo_compo_cod
FROM
public.proyectos,
public.grup_de_eval,
unidad_organi
where grup_de_eval.profe_evaluador_cod='69' and unidad_organi.unidad_orga_cod=grup_de_eval.unidad_orga_cod and
public.grup_de_eval.cod_car=  proyectos.proyec_cod order by proyectos.proyec_nom,grup_de_eval.prof_nom_ape_evaluado ASC
