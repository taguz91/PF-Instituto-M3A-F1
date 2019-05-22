SELECT * FROM public."Materias"
TRANSLATE(materia_nombre,'ÁÉÍÓÚáéíóú','AEIOUaeiou') ILIKE '%TRANSLATE(aguja,'ÁÉÍÓÚáéíóú','AEIOUaeiou')%'
